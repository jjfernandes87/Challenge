package br.com.mouzinho.marvelapp.view.characters

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter

sealed class CharactersViewState {
    object Loading : CharactersViewState()
    data class CharactersLoaded(val characters: PagedList<MarvelCharacter>) : CharactersViewState()
    data class Error(val message: String) : CharactersViewState()
    data class FavoriteUpdateError(val message: String) : CharactersViewState()
    data class FavoriteSaved(val character: MarvelCharacter) : CharactersViewState()
    data class FavoriteRemoved(val character: MarvelCharacter) : CharactersViewState()
}