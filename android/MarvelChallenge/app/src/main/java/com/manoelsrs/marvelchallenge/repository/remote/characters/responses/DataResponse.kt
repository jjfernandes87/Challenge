package com.manoelsrs.marvelchallenge.repository.remote.characters.responses

data class DataResponse(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<ResultsResponse>
)