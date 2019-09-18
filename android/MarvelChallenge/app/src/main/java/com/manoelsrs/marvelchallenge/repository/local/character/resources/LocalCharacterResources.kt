package com.manoelsrs.marvelchallenge.repository.local.character.resources

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character

interface LocalCharacterResources {
    fun getCharacters(): DataSource.Factory<Int, Character>
    fun insert(characters: List<Character>)
    fun insert(character: Character)
    fun delete(character: Character)
    fun deleteAll()
}