package paixao.leonardo.marvel.heroes.feature.character

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.stateMachine

internal class CharacterViewModel(private val characterService: CharacterService) : ViewModel() {
    fun retrieveCharacters(): Flow<StateMachineEvent<List<Character>>> = stateMachine {
        characterService.retrieveCharacter()
    }
}