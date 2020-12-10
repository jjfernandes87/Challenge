package br.com.mouzinho.data.entity.character

data class ApiComics(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ApiItem>?,
    val returned: Int?
)