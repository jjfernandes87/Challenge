package com.manoelsrs.marvelchallenge.repository

import com.manoelsrs.marvelchallenge.repository.local.LocalFactory
import com.manoelsrs.marvelchallenge.repository.local.LocalRepository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.remote.RemoteFactory
import com.manoelsrs.marvelchallenge.repository.remote.RemoteRepository

class Repository(
    charactersDatabase: CharactersDatabase,
    favoriteDatabase: FavoriteDatabase
) : RepositoryFactory {
    override val local: LocalFactory = LocalRepository(charactersDatabase, favoriteDatabase)
    override val remote: RemoteFactory = RemoteRepository()
}