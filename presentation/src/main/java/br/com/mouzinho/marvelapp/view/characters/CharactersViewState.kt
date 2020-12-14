package br.com.mouzinho.marvelapp.view.characters

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter

sealed class CharactersViewState {
    data class CharactersLoaded(val characters: PagedList<MarvelCharacter>) : CharactersViewState()
    data class ToggleLoading(val isLoading: Boolean) : CharactersViewState()
    data class ToggleEmptyView(val isEmpty: Boolean, val fromSearch: Boolean = false) : CharactersViewState()
    data class Error(val message: String) : CharactersViewState()
    data class FavoriteUpdateError(val message: String) : CharactersViewState()
    data class FavoriteSaved(val character: MarvelCharacter) : CharactersViewState()
    data class FavoriteRemoved(val character: MarvelCharacter) : CharactersViewState()
}