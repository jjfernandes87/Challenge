package br.com.mouzinho.domain.interactor.favorite

import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import javax.inject.Inject

class GetFavorites @Inject constructor(
    private val repository: FavoritesMarvelCharacterRepository
){

    operator fun invoke() = repository.loadAllFavorites()
}