package paixao.leonardo.marvel.heroes.feature.core.exceptions

sealed class MarvelException : Throwable() {

    data class UnknownMarvelError(val error: Throwable) : MarvelException()

    sealed class NetworkException() : MarvelException() {

        data class NoInternetConnection(val error: Throwable) : NetworkException()

        data class AuthException(val error: Throwable) : NetworkException()

        data class Timeout(val error: Throwable) : NetworkException()

        data class HttpException(val error: Throwable) : NetworkException()

        data class RequestException(val error: Throwable) : NetworkException()

        data class ResourceNotFound(val error: Throwable) : MarvelException()
    }
}

data class MarvelSerializationException(val error: Throwable) : MarvelException()
