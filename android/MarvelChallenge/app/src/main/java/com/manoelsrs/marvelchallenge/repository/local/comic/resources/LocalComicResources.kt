package com.manoelsrs.marvelchallenge.repository.local.comic.resources

import com.manoelsrs.marvelchallenge.model.Data
import io.reactivex.Completable
import io.reactivex.Single

interface LocalComicResources {
    fun getComicsCount(): Single<Int>
    fun getComics(): Single<List<Data>>
    fun insert(comics: List<Data>): Completable
    fun deleteAll(): Completable
}