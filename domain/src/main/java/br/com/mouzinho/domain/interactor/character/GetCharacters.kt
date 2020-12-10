package br.com.mouzinho.domain.interactor.character

import br.com.mouzinho.domain.repository.character.CharacterRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(pageSize: Int) = characterRepository.loadCharactersPagedList(pageSize)
}