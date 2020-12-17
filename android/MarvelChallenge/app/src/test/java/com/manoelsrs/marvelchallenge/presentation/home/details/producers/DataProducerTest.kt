package com.manoelsrs.marvelchallenge.presentation.home.details.producers

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSDataResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSResults
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.ThumbnailResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class DataProducerTest {

    @Test
    fun `should produce data from response`() {
        val data = listOf(
            Data(
                id = 13,
                title = "data-title",
                photo = "photo",
                photoExtension = ".png"
            )
        )

        val thumbnail = ThumbnailResponse(path = "photo", extension = ".png")
        val results = CSResults(id = 13, title = "data-title", thumbnail = thumbnail)
        val csDataResponse = CSDataResponse(results = listOf(results))
        val dataResponse = CSResponse(data = csDataResponse)

        val producer = DataProducer()

        val answer = producer.produce(dataResponse)

        assertEquals(data, answer)
    }
}