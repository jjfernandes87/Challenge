package br.com.mouzinho.domain.entity.character

data class Series(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Item>?,
    val returned: Int?
)