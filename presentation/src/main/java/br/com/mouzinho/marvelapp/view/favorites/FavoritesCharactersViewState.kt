package br.com.mouzinho.marvelapp.view.favorites

import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter

data class FavoritesCharactersViewState(
    val favorites: List<FavoriteCharacter> = emptyList(),
    val loading: Boolean = true,
    val hasError: Boolean = false,
    val errorMessage: String = ""
)