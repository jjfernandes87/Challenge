package paixao.leonardo.marvel.heroes.feature.character.screens.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.domain.services.FavoriteCharacterService
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.stateMachine

class CharacterDetailsViewModel(
    private val favoriteCharacterService: FavoriteCharacterService
) : ViewModel() {

    private var _lastCharacterState: MarvelCharacter? = null

    fun saveOrRemoveFavoriteCharacter(
        character: MarvelCharacter
    ): Flow<StateMachineEvent<Boolean>> =
        stateMachine {
            val isFavorite = (retrieveUpdatedCharacter() ?: character).isFavorite

            if (isFavorite) {
                favoriteCharacterService.removeFavoriteCharacter(character)
                _lastCharacterState = character.copy(isFavorite = false)
                false
            } else {
                favoriteCharacterService.saveFavoriteCharacter(character)
                _lastCharacterState = character.copy(isFavorite = true)
                true
            }
        }

    fun retrieveUpdatedCharacter() =
        _lastCharacterState

}