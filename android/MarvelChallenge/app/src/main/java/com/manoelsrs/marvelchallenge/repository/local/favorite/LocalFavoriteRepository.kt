package com.manoelsrs.marvelchallenge.repository.local.favorite

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDto
import com.manoelsrs.marvelchallenge.repository.local.favorite.mappers.FavoriteMapper
import com.manoelsrs.marvelchallenge.repository.local.favorite.resources.LocalFavoriteResources
import io.reactivex.Completable
import io.reactivex.Single

class LocalFavoriteRepository(private val database: FavoriteDatabase) : LocalFavoriteResources {

    override fun getFavorites(): DataSource.Factory<Int, Character> {
        return database.favoriteDao().getFavorites()
            .map { FavoriteMapper.toCharacter(it) }
    }

    override fun getFavorites(search: String): DataSource.Factory<Int, Character> {
        return database.favoriteDao().getFavoritesSearch(search)
            .map { FavoriteMapper.toCharacter(it) }
    }

    override fun getFavoritesSingle(): Single<List<Character>> {
        return database.favoriteDao().getFavoritesSingle()
            .map { FavoriteMapper.toCharacters(it) }
    }

    override fun insert(favorite: Character): Completable = Completable.fromCallable {
        database.favoriteDao().insert(FavoriteMapper.toFavoriteDto(favorite))
    }

    override fun delete(favorite: Character): Completable = Completable.fromCallable {
        database.favoriteDao().delete(FavoriteMapper.toFavoriteDto(favorite))
    }

    override fun deleteAll(): Completable = Completable.fromCallable {
        database.favoriteDao().deleteAll()
    }
}