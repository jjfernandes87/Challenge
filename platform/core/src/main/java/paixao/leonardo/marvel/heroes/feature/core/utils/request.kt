package paixao.leonardo.marvel.heroes.feature.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException

suspend fun <T> request(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> T
): T =
    supervisorScope {
        withContext(dispatcher) {
            runCatching { action() }
                .getOrElse { error ->
                    // TODO ("DO LOG HERE WITH MESSSAGE "API integration -> Failed with $error", error)
                    throw error.toInfrastructureError()
                }
        }
    }

internal fun Throwable.toInfrastructureError(): MarvelException =
    when (this) {
        else -> HttpErrorTransformer.transform(this) ?: toUnknownException()
    }

fun Throwable.toUnknownException(): MarvelException.UnknownMarvelError =
    MarvelException.UnknownMarvelError(error = this)
