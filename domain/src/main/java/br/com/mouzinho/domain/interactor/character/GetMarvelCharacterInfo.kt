package br.com.mouzinho.domain.interactor.character

import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import javax.inject.Inject

class GetMarvelCharacterInfo @Inject constructor(
    private val repository: MarvelCharacterRepository
){

    operator fun invoke(id: Int) = repository.loadCharacterInfo(id)
}