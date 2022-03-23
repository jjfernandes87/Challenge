package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.models.CharactersResponse
import paixao.leonardo.marvel.heroes.data.models.ImageResponse
import paixao.leonardo.marvel.heroes.domain.models.Character

object CharactersMapper {
    fun toDomain(response: CharactersResponse) =
        response.data.results.map { characterResponse ->
            characterResponse.run {
                Character(
                    id = id,
                    description = description,
                    name = name,
                    imageUrl = imageResponse.toDomain()
                )
            }
        }

    private fun ImageResponse.toDomain(): String =
        "$path.$extension"
}
