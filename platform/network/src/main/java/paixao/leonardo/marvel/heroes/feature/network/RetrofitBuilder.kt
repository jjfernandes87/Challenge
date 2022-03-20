package paixao.leonardo.marvel.heroes.feature.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitBuilder {
    private const val BASE_URL = "http://gateway.marvel.com/"
    private val contentType = "application/json".toMediaType()
    private val json = Json {
        ignoreUnknownKeys = true
    }

    operator fun invoke(
        apiUrl: String = BASE_URL,
        okHttpClient: OkHttpClient
    ): Retrofit =
        with(Retrofit.Builder()) {
            baseUrl(apiUrl)
            client(okHttpClient)
            addConverterFactory(json.asConverterFactory(contentType = contentType))
            build()
        }

    inline fun <reified T> Retrofit.buildGateway(): T =
        create(T::class.java)
}
