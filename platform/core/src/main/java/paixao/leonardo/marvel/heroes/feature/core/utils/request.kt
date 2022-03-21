package paixao.leonardo.marvel.heroes.feature.core.utils

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelSerializationException
import retrofit2.HttpException

suspend fun <T> request(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> T
): T =
    supervisorScope {
        withContext(dispatcher) {
            runCatching { action() }
                .getOrElse { error ->
                    Log.e("API integration -> Failed with $error", error.message.orEmpty())
                    throw error.toInfrastructureError()
                }
        }
    }

internal fun Throwable.toInfrastructureError(): MarvelException =
    when (this) {
        is SerializationException -> MarvelSerializationException(this) // TODO(NOT MAPPING TO SERIALIZATION ERROR)
        is HttpException -> HttpErrorTransformer.transform(this) ?: toUnknownException()
        else -> toUnknownException()
    }

fun Throwable.toUnknownException(): MarvelException.UnknownMarvelError =
    MarvelException.UnknownMarvelError(error = this)
