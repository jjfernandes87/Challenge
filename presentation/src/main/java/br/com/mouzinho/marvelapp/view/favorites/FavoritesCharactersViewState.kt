package br.com.mouzinho.marvelapp.view.favorites

import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter

sealed class FavoritesCharactersViewState {
    data class ShowFavorites(val favorites: List<FavoriteCharacter>) : FavoritesCharactersViewState()
    object ShowLoading : FavoritesCharactersViewState()
    object HideLoading : FavoritesCharactersViewState()
    object ShowRemovedMessage : FavoritesCharactersViewState()
    object ReloadCharacters : FavoritesCharactersViewState()
    data class ShowError(val message: String) : FavoritesCharactersViewState()
}