package paixao.leonardo.marvel.heroes.feature.character

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.stateMachine

internal class CharacterViewModel(
    private val characterService: CharacterService
) : ViewModel() {
    fun retrieveCharacters(): Flow<StateMachineEvent<List<Character>>> = stateMachine {
        characterService.retrieveCharacter()
    }

    fun retrieveFavoriteCharacter(): Flow<StateMachineEvent<List<Character>>> = stateMachine{
        characterService.retrieveFavoriteCharacters()
    }

    fun saveFavoriteCharacter(character: Character): Flow<StateMachineEvent<Unit>> = stateMachine {
        characterService.saveFavoriteCharacter(character)
    }

    fun removeFavoriteCharacter(character: Character): Flow<StateMachineEvent<Unit>> = stateMachine {
        characterService.removeFavoriteCharacter(character)
    }
}