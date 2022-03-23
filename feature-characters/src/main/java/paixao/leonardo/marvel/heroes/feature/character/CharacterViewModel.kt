package paixao.leonardo.marvel.heroes.feature.character

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.domain.services.CharactersHandler
import paixao.leonardo.marvel.heroes.domain.services.FavoriteCharacterService
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.stateMachine

internal class CharacterViewModel(
    private val charactersHandler: CharactersHandler,
    private val favoriteCharacterService: FavoriteCharacterService
) : ViewModel() {
    private val _lastUpdatedCharacters = mutableMapOf<Int, Boolean>()

    fun retrieveCharacters(): Flow<StateMachineEvent<List<MarvelCharacter>>> = stateMachine {
        charactersHandler.retrieveCharacters()
    }

    fun retrieveFavoriteCharacter(): Flow<StateMachineEvent<List<MarvelCharacter>>> = stateMachine {
        favoriteCharacterService.retrieveFavoriteCharacters()
    }

    fun saveOrRemoveFavoriteCharacter(
        character: MarvelCharacter
    ): Flow<StateMachineEvent<Boolean>> =
        stateMachine {
            val isFavorite = retrieveRealFavoriteStatus(character)
            if (isFavorite) {
                favoriteCharacterService.removeFavoriteCharacter(character)
                _lastUpdatedCharacters[character.id] = false
                false
            } else {
                favoriteCharacterService.saveFavoriteCharacter(character)
                _lastUpdatedCharacters[character.id] = true
                true
            }
        }

    fun removeFavoriteCharacter(
        character: MarvelCharacter
    ): Flow<StateMachineEvent<Boolean>> =
        stateMachine {
            favoriteCharacterService.removeFavoriteCharacter(character)
            _lastUpdatedCharacters[character.id] = false
            false
        }

    fun updateFavoriteCharactersComponent(character: MarvelCharacter) {
        //TODO()
    }

    fun retrieveRealFavoriteStatus(character: MarvelCharacter) =
        _lastUpdatedCharacters.getOrDefault(character.id, character.isFavorite)
}