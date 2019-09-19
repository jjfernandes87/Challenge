package com.manoelsrs.marvelchallenge.repository.local

import com.manoelsrs.marvelchallenge.repository.local.character.LocalCharacterRepository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources
import com.manoelsrs.marvelchallenge.repository.local.favorite.LocalFavoriteRepository
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.resources.LocalFavoriteResources

class LocalRepository(
    charactersDatabase: CharactersDatabase,
    favoriteDatabase: FavoriteDatabase
) : LocalFactory {
    override val character: LocalCharacterResources = LocalCharacterRepository(charactersDatabase)
    override val favorite: LocalFavoriteResources = LocalFavoriteRepository(favoriteDatabase)
}