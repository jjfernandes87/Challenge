package com.manoelsrs.marvelchallenge.presentation.home.characters.producers

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.ResultsResponse

class CharacterProducer {

    fun execute(results: ResultsResponse): Character {
        return Character(
            id = results.id,
            name = results.name,
            description = results.description,
            photo = results.thumbnail.path,
            photoExtension = results.thumbnail.extension,
            hasComics = results.comics.items.isNotEmpty(),
            hasSeries = results.series.items.isNotEmpty()
        )
    }
}