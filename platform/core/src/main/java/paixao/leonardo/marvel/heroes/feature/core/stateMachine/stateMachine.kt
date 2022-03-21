package paixao.leonardo.marvel.heroes.feature.core.stateMachine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent.Failure
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent.Finish
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent.Start
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent.Success

fun <T> stateMachine(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    errorTransformer: StateMachineErrorTransformer = BypassErrorTransformer(),
    action: suspend () -> T
): Flow<StateMachineEvent<T>> =
    flow<StateMachineEvent<T>> { emit(Success(action())) }
        .catch { exception -> emit(Failure(errorTransformer.apply(exception.toMarvelException()))) }
        .onStart { emit(Start) }
        .onCompletion { emit(Finish) }
        .flowOn(dispatcher)

private fun Throwable.toMarvelException(): MarvelException =
    MarvelException.UnknownMarvelError(this)
