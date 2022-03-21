package paixao.leonardo.marvel.heroes.feature.core.utils

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import paixao.leonardo.marvel.heroes.feature.core.exceptions.ErrorResponse
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.HttpURLConnection.HTTP_UNAVAILABLE
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal object HttpErrorTransformer {

    private const val RESOURCE_NOT_FOUND_ERROR = "ResourceNotFound"
    private const val API_REQUEST_ERROR = 409

    fun transform(error: Throwable): MarvelException? =
        when (error) {
            is HttpException -> mapErrorWith(error)
            is SocketTimeoutException,
            is SocketException -> MarvelException.NetworkException.Timeout(error)
            is UnknownHostException,
            is ConnectException -> MarvelException.NetworkException.NoInternetConnection(error)
            else -> null
        }

    private fun mapErrorWith(error: HttpException) =
        when (error.code()) {
            API_REQUEST_ERROR -> MarvelException.NetworkException.RequestException(error)
            HTTP_NOT_FOUND -> error.verifyForResourceNotFoundError()
            HTTP_UNAUTHORIZED -> MarvelException.NetworkException.AuthException(error)
            HTTP_CLIENT_TIMEOUT -> MarvelException.NetworkException.Timeout(error)
            HTTP_UNAVAILABLE -> MarvelException.NetworkException.NoInternetConnection(error)
            else -> MarvelException.NetworkException.HttpException(error)
        }

    private fun HttpException.verifyForResourceNotFoundError(): MarvelException {
        val rawErrorString = response()?.errorBody()?.string() ?: ""
        val errorResponse =
            try {
                Json.decodeFromString<ErrorResponse>(rawErrorString.trim())
            } catch (error: SerializationException) {
                null
            }
        return if (errorResponse?.code == RESOURCE_NOT_FOUND_ERROR)
            MarvelException.NetworkException.ResourceNotFound(this)
        else
            MarvelException.NetworkException.HttpException(this)
    }
}
