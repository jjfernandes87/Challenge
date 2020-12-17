package com.manoelsrs.marvelchallenge.repository.remote.characters.responses

data class ResultsResponse(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Any,
    val resourceURI: String,
    val urls: Any,
    val thumbnail: ThumbnailResponse,
    val comics: CommonItemsResponse,
    val stories: Any,
    val events: Any,
    val series: CommonItemsResponse
)