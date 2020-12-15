package br.com.mouzinho.data.entity

data class ApiData(
    val count: Int?,
    val limit: Int?,
    val offset: Int?,
    val results: List<ApiMarvelCharacter>?,
    val total: Int?
)