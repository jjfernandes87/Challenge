package com.manoelsrs.marvelchallenge.repository.local.favorite.mappers

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDto
import org.junit.Assert.assertEquals
import org.junit.Test

class FavoriteMapperTest {

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

    private val dto = FavoriteDto(
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
        val answer = FavoriteMapper.toCharacter(dto)
        assertEquals(character, answer)
    }

    @Test
    fun `should map dtos to characters`() {
        val answer = FavoriteMapper.toCharacters(dtos)
        assertEquals(characters, answer)
    }

    @Test
    fun `should map character to dto`() {
        val answer = FavoriteMapper.toFavoriteDto(character)
        assertEquals(dto, answer)
    }
}