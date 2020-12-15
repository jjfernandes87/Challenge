package br.com.mouzinho.data.entity

data class ApiDiaResponse(
    val data: ApiMediaResult
)

data class ApiMediaResult(
    val results: List<ApiMediaDetails>
)