package br.com.mouzinho.domain.entity.character

sealed class CharacterResult {
    data class Success(val data: List<Character>) : CharacterResult()
    data class Failure(val throwable: Throwable) : CharacterResult()
    object Loading : CharacterResult()
}