package br.com.mouzinho.data.entity

data class ApiComicResponse(
    val data: ApiComicResult
)

data class ApiComicResult(
    val results: List<ApiComicDetails>
)