package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.marvelapp.di.MarvelQueryParams.Companion.PRIVATE_API_KEY_VALUE
import br.com.mouzinho.marvelapp.di.MarvelQueryParams.Companion.PUBLIC_API_KEY_VALUE
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    private const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val API_BASE_URL = "https://gateway.marvel.com:443/"
    private const val API_KEY_QUERY = "apikey"
    private const val API_HASH_QUERY = "hash"
    private const val API_TIMESTAMP_QUERY = "ts"
    private const val NAMED_QUERY_PARAMS = "NAMED_QUERY_PARAMS"

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .build()

    @Provides
    fun provideOkHttp(apiKeyInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()

    @Provides
    fun providesApiKeyInterceptor(
        @Named(NAMED_QUERY_PARAMS) queryParams: MarvelQueryParams
    ) = Interceptor { chain ->
        val newUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY_QUERY, queryParams.publicApiKey)
            .addQueryParameter(API_HASH_QUERY, queryParams.hash)
            .addQueryParameter(API_TIMESTAMP_QUERY, queryParams.timeStamp)
        chain.proceed(chain.request().newBuilder().url(newUrl.build()).build())
    }

    @Provides
    @Named(NAMED_QUERY_PARAMS)
    fun providesQueryParams(): MarvelQueryParams {
        val timeStamp = System.currentTimeMillis().toString()
        val messageDigest =  MessageDigest.getInstance("MD5")
        messageDigest.update(timeStamp.toByteArray())
        messageDigest.update(PRIVATE_API_KEY_VALUE.toByteArray())
        messageDigest.update(PUBLIC_API_KEY_VALUE.toByteArray())
        val hash = messageDigest.digest().joinToString("") { "%02x".format(it) }
        return MarvelQueryParams(PUBLIC_API_KEY_VALUE, timeStamp, hash)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().serializeNulls().setDateFormat(API_DATE_FORMAT).create()
}