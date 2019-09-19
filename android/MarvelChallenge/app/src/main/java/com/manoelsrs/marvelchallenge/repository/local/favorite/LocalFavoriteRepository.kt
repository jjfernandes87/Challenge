package com.manoelsrs.marvelchallenge.repository.local.favorite

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDto
import com.manoelsrs.marvelchallenge.repository.local.favorite.resources.LocalFavoriteResources
import io.reactivex.Completable
import io.reactivex.Single

class LocalFavoriteRepository(private val database: FavoriteDatabase) : LocalFavoriteResources {

    override fun getFavorites(): Single<List<Character>> {
        return database.favoriteDao().getFavorites()
            .map { favorites ->
                favorites.map { Character(it.id, it.name, it.photo, it.photoExtension) }
            }
    }

    override fun insert(favorite: Character): Completable = Completable.fromCallable {
        database.favoriteDao().insert(
            FavoriteDto(
                favorite.id,
                favorite.name,
                favorite.photo,
                favorite.photoExtension
            )
        )
    }

    override fun delete(favorite: Character): Completable = Completable.fromCallable {
        database.favoriteDao().delete(
            FavoriteDto(
                favorite.id,
                favorite.name,
                favorite.photo,
                favorite.photoExtension
            )
        )
    }

    override fun deleteAll(): Completable = Completable.fromCallable {
        database.favoriteDao().deleteAll()
    }
}