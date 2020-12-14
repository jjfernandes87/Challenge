package br.com.mouzinho.domain.repository.favorite

import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import io.reactivex.Observable
import io.reactivex.Single

interface FavoritesMarvelCharacterRepository {

    fun getFavoriteById(id: Int): Single<List<FavoriteCharacter>>
    fun saveAsFavorite(character: MarvelCharacter): Single<Boolean>
    fun removeFromFavorites(character: MarvelCharacter): Single<Boolean>
    fun removeFromFavorites(favorite: FavoriteCharacter): Single<Boolean>
    fun loadAllFavorites(): Observable<List<FavoriteCharacter>>
    fun search(name: String): Observable<List<FavoriteCharacter>>
}