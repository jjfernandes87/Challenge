package com.manoelsrs.marvelchallenge.repository.local.character.mappers

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDto
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMapperTest {

    private val character = Character(
        id = 1,
        name = "name test",
        description = "description",
        photo = "photo",
        photoExtension = ".jpg",
        hasSeries = true,
        hasComics = false
    )

    private val characters = listOf(
        character,
        character.copy(id = 2),
        character.copy(id = 3),
        character.copy(id = 4)
    )

    private val dto = CharactersDto(
        id = 1,
        name = "name test",
        description = "description",
        photo = "photo",
        photoExtension = ".jpg",
        hasSeries = true,
        hasComics = false
    )

    private val dtos = listOf(dto, dto.copy(2), dto.copy(id = 3), dto.copy(id = 4))

    @Test
    fun `should map dto to character`() {
        val answer = CharacterMapper.toCharacter(dto)
        assertEquals(character, answer)
    }

    @Test
    fun `should map characters to dtos`() {
        val answer = CharacterMapper.toCharactersDto(characters)
        assertEquals(dtos, answer)
    }

    @Test
    fun `should map character to dto`() {
        val answer = CharacterMapper.toCharacterDto(character)
        assertEquals(dto, answer)
    }
}