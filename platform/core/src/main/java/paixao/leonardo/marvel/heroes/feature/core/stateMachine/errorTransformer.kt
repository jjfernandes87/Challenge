package paixao.leonardo.marvel.heroes.feature.core.stateMachine

import paixao.leonardo.marvel.heroes.feature.core.exceptions.MarvelException

interface StateMachineErrorTransformer {

    fun apply(exception: MarvelException): MarvelException
}

class BypassErrorTransformer : StateMachineErrorTransformer {

    override fun apply(exception: MarvelException): MarvelException = exception
}
