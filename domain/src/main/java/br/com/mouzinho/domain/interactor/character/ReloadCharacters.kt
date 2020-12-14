package br.com.mouzinho.domain.interactor.character

import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import javax.inject.Inject

class ReloadCharacters @Inject constructor(private val repository: MarvelCharacterRepository) {

    operator fun invoke() = repository.reload()
}