package paixao.leonardo.marvel.heroes.feature.core.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String
)
