package com.manoelsrs.marvelchallenge.repository.local.favorite.resources

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character
import io.reactivex.Completable
import io.reactivex.Single

interface LocalFavoriteResources {
    fun getFavorites(): DataSource.Factory<Int, Character>
    fun getFavorites(content: String): Single<List<Character>>
    fun insert(favorite: Character): Completable
    fun delete(favorite: Character): Completable
    fun deleteAll(): Completable
}