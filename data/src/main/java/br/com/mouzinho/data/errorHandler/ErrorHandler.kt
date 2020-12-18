package br.com.mouzinho.data.errorHandler

import retrofit2.HttpException

object ErrorHandler {

    fun Throwable.toAppError(): Throwable {
        return Throwable(
            if (this is HttpException) {
                "Não foi possível comunicar com nossos servidores, verifique sua conexão com a internet e tente novamente."
            } else {
                "Não foi possível obter a lista de personagens, por favor tente novamente mais tarde."
            }
        )
    }
}