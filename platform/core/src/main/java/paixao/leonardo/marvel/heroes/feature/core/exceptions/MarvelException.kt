package paixao.leonardo.marvel.heroes.feature.core.exceptions

sealed class MarvelException : Throwable() {
// TODO()
}

data class UnknownMarvelError(val error: Throwable) : MarvelException()
