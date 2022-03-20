package paixao.leonardo.marvel.heroes.lib

import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.lib.models.CharactersResponse
import paixao.leonardo.marvel.heroes.lib.models.ImageResponse

object CharactersMapper {

    fun toDomain(response: CharactersResponse) =
        response.data.results.map { characterResponse ->
            characterResponse.run {
                Character(
                    name = name,
                    imageUrl = imageResponse.toDomain()
                )
            }
        }
}

private fun ImageResponse.toDomain(): String =
    path + extension
