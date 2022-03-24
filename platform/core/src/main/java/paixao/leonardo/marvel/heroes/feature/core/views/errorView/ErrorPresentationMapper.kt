package paixao.leonardo.marvel.heroes.feature.core.views.errorView

import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelSerializationException

object ErrorPresentationMapper {
    operator fun invoke(marvelException: MarvelException): ErrorPresentation =
        when (marvelException) {
            is MarvelSerializationException -> ErrorPresentation.SERIALIZATION_ISSUES

            is MarvelException.NetworkException.ResourceNotFound -> ErrorPresentation.NOT_FOUND

            is MarvelException.NetworkException.AuthException,
            is MarvelException.NetworkException.NoInternetConnection,
            is MarvelException.NetworkException.Timeout -> ErrorPresentation.CONNECTION_ISSUES

            is MarvelException.NetworkException.HttpException,
            is MarvelException.NetworkException.RequestException,
            is MarvelException.UnknownMarvelError -> ErrorPresentation.UNKNOWN_ERROR
        }
}
