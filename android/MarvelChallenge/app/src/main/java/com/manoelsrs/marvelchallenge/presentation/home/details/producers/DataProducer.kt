package com.manoelsrs.marvelchallenge.presentation.home.details.producers

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSResponse

class DataProducer {

    fun produce(response: CSResponse): List<Data> {
        return response.data.results.map {
            Data(
                id = it.id,
                title = it.title,
                photo = it.thumbnail?.path ?: "",
                photoExtension = it.thumbnail?.extension ?: ""
            )
        }
    }
}