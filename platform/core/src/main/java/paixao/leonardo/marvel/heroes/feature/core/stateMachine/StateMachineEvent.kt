package paixao.leonardo.marvel.heroes.feature.core.stateMachine

import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException

sealed class StateMachineEvent<out T> {
    object Start : StateMachineEvent<Nothing>()
    data class Success<out T>(val value: T) : StateMachineEvent<T>()
    data class Failure(val exception: MarvelException) : StateMachineEvent<Nothing>()
    object Finish : StateMachineEvent<Nothing>()
}
