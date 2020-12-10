package br.com.mouzinho.data.entity.character

data class ApiStories(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ApiItem>?,
    val returned: Int?
)