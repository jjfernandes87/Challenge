package com.manoelsrs.marvelchallenge.repository.local.comic

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDatabase
import com.manoelsrs.marvelchallenge.repository.local.comic.mappers.ComicMapper
import com.manoelsrs.marvelchallenge.repository.local.comic.resources.LocalComicResources
import io.reactivex.Completable
import io.reactivex.Single

class LocalComicRepository(private val database: ComicDatabase) : LocalComicResources {

    override fun getComicsCount(): Single<Int> {
        return database.comicDao().getComicsCount()
    }

    override fun getComics(): Single<List<Data>> {
        return database.comicDao().getComics()
            .map { ComicMapper.toData(it) }
    }

    override fun insert(comics: List<Data>): Completable = Completable.fromCallable {
        database.comicDao().insert(ComicMapper.toComicsDto(comics))
    }

    override fun deleteAll(): Completable = Completable.fromCallable {
        database.comicDao().deleteAll()
    }
}