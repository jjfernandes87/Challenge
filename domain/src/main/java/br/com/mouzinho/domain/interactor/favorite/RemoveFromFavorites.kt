package br.com.mouzinho.domain.interactor.favorite

import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import javax.inject.Inject

class RemoveFromFavorites @Inject constructor(
    private val repository: FavoritesMarvelCharacterRepository
) {

    operator fun invoke(favorite: FavoriteCharacter) = repository.removeFromFavorites(favorite)
}