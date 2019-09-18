package com.manoelsrs.marvelchallenge.repository.local.character.resources

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character
import io.reactivex.Single

interface LocalCharacterResources {
    fun getCharactersCount(): Single<Int>
    fun getCharacters(): DataSource.Factory<Int, Character>
    fun insert(characters: List<Character>)
    fun insert(character: Character)
    fun delete(character: Character)
    fun deleteAll()
}