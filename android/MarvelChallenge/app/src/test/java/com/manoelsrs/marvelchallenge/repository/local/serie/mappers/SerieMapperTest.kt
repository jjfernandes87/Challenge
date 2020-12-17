package com.manoelsrs.marvelchallenge.repository.local.serie.mappers

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDto
import org.junit.Assert.assertEquals
import org.junit.Test

class SerieMapperTest {

    private val dto = SerieDto(
        id = 1,
        title = "title",
        photo = "photo",
        photoExtension = ".jpg"
    )

    private val dtos = listOf(dto, dto.copy(id = 2), dto.copy(id = 3))

    private val serie = Data(
        id = 1,
        title = "title",
        photo = "photo",
        photoExtension = ".jpg"
    )

    private val series = listOf(serie, serie.copy(id = 2), serie.copy(id = 3))

    @Test
    fun `should map dtos to series`() {
        val answer = SerieMapper.toData(dtos)
        assertEquals(series, answer)
    }

    @Test
    fun `should map series to dtos`() {
        val answer = SerieMapper.toSeriesDto(series)
        assertEquals(dtos, answer)
    }
}