package com.manoelsrs.marvelchallenge.repository.local

import com.manoelsrs.marvelchallenge.repository.local.character.LocalCharacterRepository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources
import com.manoelsrs.marvelchallenge.repository.local.comic.LocalComicRepository
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDatabase
import com.manoelsrs.marvelchallenge.repository.local.comic.resources.LocalComicResources
import com.manoelsrs.marvelchallenge.repository.local.favorite.LocalFavoriteRepository
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.resources.LocalFavoriteResources
import com.manoelsrs.marvelchallenge.repository.local.serie.LocalSerieRepository
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDatabase
import com.manoelsrs.marvelchallenge.repository.local.serie.resources.LocalSerieResources

class LocalRepository(
    charactersDatabase: CharactersDatabase,
    comicDatabase: ComicDatabase,
    favoriteDatabase: FavoriteDatabase,
    serieDatabase: SerieDatabase
) : LocalFactory {
    override val character: LocalCharacterResources = LocalCharacterRepository(charactersDatabase)
    override val comic: LocalComicResources = LocalComicRepository(comicDatabase)
    override val favorite: LocalFavoriteResources = LocalFavoriteRepository(favoriteDatabase)
    override val serie: LocalSerieResources = LocalSerieRepository(serieDatabase)
}