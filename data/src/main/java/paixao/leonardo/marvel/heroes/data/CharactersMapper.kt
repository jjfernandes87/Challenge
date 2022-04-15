package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.models.CharactersResponse
import paixao.leonardo.marvel.heroes.data.models.ImageResponse
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter

object CharactersMapper {
    fun toDomain(response: CharactersResponse, offSet: Int) =
        response.data.results.mapIndexed { index, characterResponse ->
            characterResponse.run {
                val realPosition = index + offSet
                MarvelCharacter(
                    id = id,
                    description = description,
                    name = name,
                    imageUrl = imageResponse.toDomain(),
                    position = realPosition
                )
            }
        }

    private fun ImageResponse.toDomain(): String =
        "$path.$extension"
}
