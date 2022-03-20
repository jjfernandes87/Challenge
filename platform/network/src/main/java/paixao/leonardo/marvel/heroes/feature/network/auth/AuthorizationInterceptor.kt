package paixao.leonardo.marvel.heroes.feature.network.auth

import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val authorizationProvider: AuthorizationProvider
) : Interceptor {

    private companion object {
        const val PUBLIC_KEY = "apiKey"
        const val HASH_KEY = "hash"
        const val TIME_STAMP_KEY = "ts"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizationParams = authorizationProvider()
        val authHeaders = authorizationParams.run {
            mapOf(
                TIME_STAMP_KEY to timeStampAsString,
                PUBLIC_KEY to publicKey,
                HASH_KEY to hash
            ).toHeaders()
        }
        val request = chain
            .request()
            .newBuilder()
            .headers(authHeaders)

        return chain.proceed(request.build())
    }
}
