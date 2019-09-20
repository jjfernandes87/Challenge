package com.manoelsrs.marvelchallenge.repository

import com.manoelsrs.marvelchallenge.repository.local.LocalFactory
import com.manoelsrs.marvelchallenge.repository.local.LocalRepository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDatabase
import com.manoelsrs.marvelchallenge.repository.remote.RemoteFactory
import com.manoelsrs.marvelchallenge.repository.remote.RemoteRepository

class Repository(
    charactersDatabase: CharactersDatabase,
    comicDatabase: ComicDatabase,
    favoriteDatabase: FavoriteDatabase,
    serieDatabase: SerieDatabase
) : RepositoryFactory {

    override val local: LocalFactory = LocalRepository(
        charactersDatabase,
        comicDatabase,
        favoriteDatabase,
        serieDatabase
    )
    override val remote: RemoteFactory = RemoteRepository()
}