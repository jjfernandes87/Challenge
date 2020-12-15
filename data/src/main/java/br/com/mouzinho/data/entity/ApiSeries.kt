package br.com.mouzinho.data.entity

data class ApiSeries(
    val available: Int?,
    val collectionURI: String?,
    val items: List<ApiItem>?,
    val returned: Int?
)