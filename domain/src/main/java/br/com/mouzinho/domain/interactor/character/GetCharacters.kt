package br.com.mouzinho.domain.interactor.character

import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val marvelCharacterRepository: MarvelCharacterRepository
) {

    operator fun invoke(pageSize: Int) = marvelCharacterRepository.loadCharactersPagedList(pageSize)
}