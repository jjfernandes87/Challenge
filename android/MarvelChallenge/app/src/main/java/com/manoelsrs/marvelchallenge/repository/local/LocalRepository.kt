package com.manoelsrs.marvelchallenge.repository.local

import com.manoelsrs.marvelchallenge.repository.local.character.LocalCharacterRepository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources

class LocalRepository(
    charactersDatabase: CharactersDatabase
) : LocalFactory {
    override val character: LocalCharacterResources = LocalCharacterRepository(charactersDatabase)
}