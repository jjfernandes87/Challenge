package paixao.leonardo.marvel.heroes.feature.core.utils

import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.exceptions.NetworkException
import retrofit2.HttpException

internal object HttpErrorTransformer {

    fun transform(error: Throwable): MarvelException? =
        when (error) {
            is HttpException -> mapErrorWith(error.code())
            else -> null
        }

    private fun mapErrorWith(code: Int) =
        when (code) {
            401 -> NetworkException.AuthorizationException
            else -> null
        }
}
