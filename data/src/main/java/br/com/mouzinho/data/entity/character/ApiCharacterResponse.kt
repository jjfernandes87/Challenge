package br.com.mouzinho.data.entity.character

data class ApiCharacterResponse(
    val attributionHTML: String?,
    val attributionText: String?,
    val code: Int?,
    val copyright: String?,
    val data: ApiData?,
    val etag: String?,
    val status: String?
)