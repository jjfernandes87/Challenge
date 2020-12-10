package br.com.mouzinho.domain.interactor.character

import br.com.mouzinho.domain.repository.character.CharacterRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(limit: Int, offset: Int) = characterRepository.getCharacters(limit, offset)
}