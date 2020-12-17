package com.manoelsrs.marvelchallenge.repository.remote.characters.responses

data class CommonItemsResponse(
    val available: Int,
    val returned: Int,
    val collectionURI: String,
    val items: List<ItemsResponse>
)