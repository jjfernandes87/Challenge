package com.manoelsrs.marvelchallenge.repository.local.comic.mappers

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ComicMapperTest {

    private val dto = ComicDto(
        id = 1,
        title = "title",
        photo = "photo",
        photoExtension = ".jpg"
    )

    private val dtos = listOf(dto, dto.copy(id = 2), dto.copy(id = 3))

    private val comic = Data(
        id = 1,
        title = "title",
        photo = "photo",
        photoExtension = ".jpg"
    )

    private val comics = listOf(comic, comic.copy(id = 2), comic.copy(id = 3))

    @Test
    fun `should map dtos to comics`() {
        val answer = ComicMapper.toData(dtos)
        assertEquals(comics, answer)
    }

    @Test
    fun `should map comics to dtos`() {
        val answer = ComicMapper.toComicsDto(comics)
        assertEquals(dtos, answer)
    }
}