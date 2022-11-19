package com.manoelsrs.marvelchallenge.repository.local

import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources
import com.manoelsrs.marvelchallenge.repository.local.comic.resources.LocalComicResources
import com.manoelsrs.marvelchallenge.repository.local.favorite.resources.LocalFavoriteResources
import com.manoelsrs.marvelchallenge.repository.local.serie.resources.LocalSerieResources

interface LocalFactory {
    val character: LocalCharacterResources
    val comic: LocalComicResources
    val favorite: LocalFavoriteResources
    val serie: LocalSerieResources
}