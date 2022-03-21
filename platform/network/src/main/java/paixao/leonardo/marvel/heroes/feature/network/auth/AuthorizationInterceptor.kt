package paixao.leonardo.marvel.heroes.feature.network.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val authorizationProvider: AuthorizationProvider
) : Interceptor {

    private companion object {
        const val PUBLIC_KEY = "apikey"
        const val HASH_KEY = "hash"
        const val TIME_STAMP_KEY = "ts"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizationParams = authorizationProvider()
        val original = chain.request()
        val originalHttpUrl = original.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(PUBLIC_KEY, authorizationParams.publicKey)
            .addQueryParameter(HASH_KEY, authorizationParams.hash)
            .addQueryParameter(TIME_STAMP_KEY, authorizationParams.timeStampAsString)
            .build()

        val newRequest = original
            .newBuilder()
            .url(newUrl)

        return chain.proceed(newRequest.build())
    }
}
