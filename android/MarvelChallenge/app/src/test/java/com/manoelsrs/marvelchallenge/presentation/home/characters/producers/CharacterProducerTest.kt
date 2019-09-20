package com.manoelsrs.marvelchallenge.presentation.home.characters.producers

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CommonItemsResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.ItemsResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.ResultsResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.ThumbnailResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterProducerTest {

    private val character = Character(
        id = 1,
        name = "name",
        description = "description",
        photo = "photo",
        photoExtension = "photoExt",
        hasComics = false,
        hasSeries = false
    )

    private val thumbnail = ThumbnailResponse("photo", "photoExt")
    private val item = CommonItemsResponse(1,1,"", listOf())
    private val response = ResultsResponse(
        id = 1,
        name = "name",
        description = "description",
        modified = Any(),
        resourceURI = "",
        urls = Any(),
        thumbnail = thumbnail,
        comics = item,
        stories = Any(),
        events = Any(),
        series = item
    )

    private val producer = CharacterProducer()

    @Test
    fun `should produce character`() {
        val answer = producer.execute(response)
        assertEquals(character, answer)
    }

    @Test
    fun `should produce character and hasComics true`() {
        val comic = ItemsResponse("", "comic")
        val answer = producer.execute(response.copy(comics = item.copy(items = listOf(comic))))
        assertEquals(character.copy(hasComics = true), answer)
    }

    @Test
    fun `should produce character and hasSeries true`() {
        val serie = ItemsResponse("", "serie")
        val answer = producer.execute(response.copy(series = item.copy(items = listOf(serie))))
        assertEquals(character.copy(hasSeries = true), answer)
    }

    @Test
    fun `should produce character and flags true`() {
        val serie = ItemsResponse("", "serie")
        val comic = ItemsResponse("", "comic")
        val answer = producer.execute(response.copy(
            comics = item.copy(items = listOf(comic)),
            series = item.copy(items = listOf(serie)))
        )
        assertEquals(character.copy(hasComics = true, hasSeries = true), answer)
    }
}