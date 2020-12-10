package br.com.mouzinho.data.entity.character

data class ApiData(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val results: List<ApiCharacter>?,
    val total: Int?
)