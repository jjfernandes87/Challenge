package br.com.mouzinho.domain.interactor.character

import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import javax.inject.Inject

class SearchCharacter @Inject constructor(
    private val repository: MarvelCharacterRepository
) {

    operator fun invoke(name: String) = repository.sendSearchNameToPagingSource(name)
}