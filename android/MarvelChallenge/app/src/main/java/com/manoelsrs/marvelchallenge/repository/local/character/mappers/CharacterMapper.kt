package com.manoelsrs.marvelchallenge.repository.local.character.mappers

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDto

object CharacterMapper {

    fun toCharacter(dto: CharactersDto): Character {
        return Character(
            dto.id,
            dto.name,
            dto.description,
            dto.photo,
            dto.photoExtension,
            dto.hasComics,
            dto.hasSeries
        )
    }

    fun toCharactersDto(characters: List<Character>): List<CharactersDto> {
        return characters.map {
            CharactersDto(
                it.id,
                it.name,
                it.description,
                it.photo,
                it.photoExtension,
                it.hasComics,
                it.hasSeries
            )
        }
    }

    fun toCharacterDto(character: Character): CharactersDto {
        return CharactersDto(
            character.id,
            character.name,
            character.description,
            character.photo,
            character.photoExtension,
            character.hasComics,
            character.hasSeries
        )
    }
}