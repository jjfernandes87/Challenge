package br.com.mouzinho.data.entity

data class ApiEvents(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ApiItem>?,
    val returned: Int?
)