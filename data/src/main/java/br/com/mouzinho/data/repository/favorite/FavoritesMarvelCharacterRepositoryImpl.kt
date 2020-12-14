package br.com.mouzinho.data.repository.favorite

import br.com.mouzinho.data.database.dao.FavoritesCharactersDao
import br.com.mouzinho.data.database.entity.DbFavoriteCharacter
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.favorite.FavoriteCharacter
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FavoritesMarvelCharacterRepositoryImpl @Inject constructor(
    private val dao: FavoritesCharactersDao,
    private val marvelCharacterToDbFavoriteMapper: Mapper<MarvelCharacter, DbFavoriteCharacter>,
    private val dbFavoriteToFavoriteMapper: Mapper<DbFavoriteCharacter, FavoriteCharacter>,
    private val favoriteToDbFavoriteMapper: Mapper<FavoriteCharacter, DbFavoriteCharacter>
) : FavoritesMarvelCharacterRepository {

    override fun getFavoriteById(id: Int): Single<List<FavoriteCharacter>> {
        return Single.fromCallable { dao.getById(id).map(dbFavoriteToFavoriteMapper::transform) }
    }

    override fun saveAsFavorite(character: MarvelCharacter): Single<Boolean> {
        return Single.fromCallable { dao.insert(marvelCharacterToDbFavoriteMapper.transform(character)) }
            .map { it != (-1).toLong() }
            .onErrorReturnItem(false)
    }

    override fun removeFromFavorites(character: MarvelCharacter): Single<Boolean> {
        return Single.fromCallable { dao.delete(marvelCharacterToDbFavoriteMapper.transform(character)) }
            .map { it != -1 }
            .onErrorReturnItem(false)
    }

    override fun removeFromFavorites(favorite: FavoriteCharacter): Single<Boolean> {
        return Single.fromCallable { dao.delete(favoriteToDbFavoriteMapper.transform(favorite)) }
            .map { it != -1 }
            .onErrorReturnItem(false)
    }

    override fun loadAllFavorites(): Observable<List<FavoriteCharacter>> {
        return dao.getAll().map(dbFavoriteToFavoriteMapper::transform).onErrorReturnItem(emptyList())
    }

    override fun search(name: String): Observable<List<FavoriteCharacter>> {
        return dao.getAllByName("%$name%").map(dbFavoriteToFavoriteMapper::transform).onErrorReturnItem(emptyList())
    }
}