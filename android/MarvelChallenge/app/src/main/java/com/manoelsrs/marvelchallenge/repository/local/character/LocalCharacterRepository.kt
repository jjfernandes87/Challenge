package com.manoelsrs.marvelchallenge.repository.local.character

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.mappers.CharacterMapper
import com.manoelsrs.marvelchallenge.repository.local.character.resources.LocalCharacterResources
import io.reactivex.Completable
import io.reactivex.Single

class LocalCharacterRepository(
    private val database: CharactersDatabase
) : LocalCharacterResources {

    override fun getCharactersCount(): Single<Int> {
        return database.charactersDao().getCharactersCount()
    }

    override fun getCharacters(): DataSource.Factory<Int, Character> {
        return database.charactersDao().getCharacters()
            .map { CharacterMapper.toCharacter(it) }
    }

    override fun insert(characters: List<Character>) {
        database.charactersDao().insert(CharacterMapper.toCharactersDto(characters))
    }

    override fun insert(character: Character) {
        database.charactersDao().insert(CharacterMapper.toCharacterDto(character))
    }

    override fun delete(character: Character) {
        database.charactersDao().delete(CharacterMapper.toCharacterDto(character))
    }

    override fun deleteAll(): Completable = Completable.fromCallable {
        database.charactersDao().deleteAll()
    }
}