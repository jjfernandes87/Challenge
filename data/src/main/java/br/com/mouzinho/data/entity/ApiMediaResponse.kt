package br.com.mouzinho.data.entity

data class ApiMediaResponse(
    val data: ApiMediaResult
)

data class ApiMediaResult(
    val results: List<ApiMediaDetails>
)