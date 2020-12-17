package com.manoelsrs.marvelchallenge.repository.remote.characters.responses

data class CharactersResponse(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: DataResponse,
    val etag: String
)