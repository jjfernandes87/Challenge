package paixao.leonardo.marvel.heroes.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    val results: List<T>,
    val total: Int,
    val offset: Int
)
