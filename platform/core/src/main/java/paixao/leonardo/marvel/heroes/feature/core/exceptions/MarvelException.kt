package paixao.leonardo.marvel.heroes.feature.core.exceptions

sealed class MarvelException : Throwable() {
    object ResourceNotFound : MarvelException()
    data class UnknownMarvelError(val error: Throwable) : MarvelException()
}
