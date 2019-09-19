package com.manoelsrs.marvelchallenge.repository.remote.characters.responses

data class CSResults(
    val id: Int,
    val title: String,
    val thumbnail: ThumbnailResponse?
)