package paixao.leonardo.marvel.heroes.feature.core.exceptions

sealed class NetworkException : MarvelException() {
    object InternetException : NetworkException()
    object AuthorizationException : NetworkException()
}
