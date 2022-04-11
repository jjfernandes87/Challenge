package paixao.leonardo.marvel.heroes.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(
    val data: PagedResponse<CharacterResponse>
)

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val description: String? = null,
    val modified: String,
    @SerialName("thumbnail") val imageResponse: ImageResponse
)

@Serializable
data class ImageResponse(
    val path: String,
    val extension: String
)
