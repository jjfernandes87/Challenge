package com.manoelsrs.marvelchallenge.repository.local.favorite.resources

import com.manoelsrs.marvelchallenge.model.Character
import io.reactivex.Completable
import io.reactivex.Single

interface LocalFavoriteResources {
    fun getFavorites(): Single<List<Character>>
    fun insert(favorite: Character): Completable
    fun delete(favorite: Character): Completable
    fun deleteAll(): Completable
}