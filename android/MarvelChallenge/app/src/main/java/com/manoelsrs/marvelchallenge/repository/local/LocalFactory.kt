package com.manoelsrs.marvelchallenge.repository.local

import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources
import com.manoelsrs.marvelchallenge.repository.local.favorite.resources.LocalFavoriteResources

interface LocalFactory {
    val character: LocalCharacterResources
    val favorite: LocalFavoriteResources
}