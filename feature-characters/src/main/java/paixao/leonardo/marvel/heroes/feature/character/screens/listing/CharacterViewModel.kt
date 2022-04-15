package paixao.leonardo.marvel.heroes.feature.character.screens.listing

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.domain.services.CharactersHandler
import paixao.leonardo.marvel.heroes.domain.services.FavoriteCharacterService
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.StateMachineEvent
import paixao.leonardo.marvel.heroes.feature.core.stateMachine.stateMachine

typealias CharacterDetailsAndImgView = Pair<MarvelCharacter, AppCompatImageView>

internal class CharacterViewModel(
    private val charactersHandler: CharactersHandler,
    private val favoriteCharacterService: FavoriteCharacterService
) : ViewModel() {
    private val _lastUpdatedCharacters = mutableMapOf<Int, Boolean>()
    private val _notifyFavoritesCharacterDataChange = MutableSharedFlow<MarvelCharacter>()
    private var _lastCharacterDetailsBeforeNavToDetails: CharacterDetailsAndImgView? = null

    fun retrieveCharacters(isRefreshing: Boolean): Flow<StateMachineEvent<List<MarvelCharacter>>> = stateMachine {
        charactersHandler.retrieveCharacters(isRefreshing)
    }

    fun retrieveFavoriteCharacter(): Flow<StateMachineEvent<List<MarvelCharacter>>> = stateMachine {
        favoriteCharacterService.retrieveFavoriteCharacters()
    }

    fun saveOrRemoveFavoriteCharacter(
        character: MarvelCharacter
    ): Flow<StateMachineEvent<Boolean>> =
        stateMachine {
            val isFavorite = retrieveRealFavoriteStatus(character)
            val result = if (isFavorite) {
                favoriteCharacterService.removeFavoriteCharacter(character)
                _lastUpdatedCharacters[character.id] = false
                false
            } else {
                favoriteCharacterService.saveFavoriteCharacter(character)
                _lastUpdatedCharacters[character.id] = true
                true
            }
            val characterWithNewState = character.copy(isFavorite = !character.isFavorite)
            _notifyFavoritesCharacterDataChange.emit(characterWithNewState)
            result
        }

    fun listenFavoriteCharactersChange() = _notifyFavoritesCharacterDataChange

    fun storeCharacterStatusBeforeNavigateToDetails(
        character: MarvelCharacter,
        imageView: AppCompatImageView
    ) {
        _lastCharacterDetailsBeforeNavToDetails = character to imageView
    }

    fun lastCharacterStatusBeforeNavigateToDetails() = _lastCharacterDetailsBeforeNavToDetails

    fun storeLastUpdatedCharacter(character: MarvelCharacter) {
        _lastCharacterDetailsBeforeNavToDetails = null
        _lastUpdatedCharacters[character.id] = character.isFavorite
    }

    fun retrieveRealFavoriteStatus(character: MarvelCharacter) =
        _lastUpdatedCharacters.getOrDefault(character.id, character.isFavorite)

    suspend fun notifyFavoriteListViewDataChanged(character: MarvelCharacter) {
        _notifyFavoritesCharacterDataChange.emit(character)
    }

}