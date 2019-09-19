package com.manoelsrs.marvelchallenge.repository.local.character

import androidx.paging.DataSource
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDto
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
            .map { Character(it.id, it.name, it.description, it.photo, it.photoExtension) }
    }

    override fun insert(characters: List<Character>) {
        database.charactersDao().insert(characters.map {
            CharactersDto(it.id, it.name, it.description, it.photo, it.photoExtension)
        })
    }

    override fun insert(character: Character) {
        database.charactersDao().insert(
            CharactersDto(
                character.id,
                character.name,
                character.description,
                character.photo,
                character.photoExtension
            )
        )
    }

    override fun delete(character: Character) {
        database.charactersDao().delete(
            CharactersDto(
                character.id,
                character.name,
                character.description,
                character.photo,
                character.photoExtension
            )
        )
    }

    override fun deleteAll(): Completable = Completable.fromCallable {
        database.charactersDao().deleteAll()
    }
}