package br.com.mouzinho.marvelapp.view.favorites

import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter

sealed class FavoritesCharactersViewState {
    data class ShowFavorites(val favorites: List<FavoriteCharacter>, val fromSearch: Boolean = false) : FavoritesCharactersViewState()
    object ShowRemovedMessage : FavoritesCharactersViewState()
    object ReloadCharacters : FavoritesCharactersViewState()
    data class ShowError(val message: String) : FavoritesCharactersViewState()
}