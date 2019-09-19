package com.manoelsrs.marvelchallenge.repository.local.favorite.resources

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character
import io.reactivex.Completable

interface LocalFavoriteResources {
    fun getFavorites(): DataSource.Factory<Int, Character>
    fun insert(favorite: Character): Completable
    fun delete(favorite: Character): Completable
    fun deleteAll(): Completable
}