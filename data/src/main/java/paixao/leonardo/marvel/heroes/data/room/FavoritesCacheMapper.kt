package paixao.leonardo.marvel.heroes.data.room

import paixao.leonardo.marvel.heroes.data.room.models.FavoriteCharacterCache
import paixao.leonardo.marvel.heroes.domain.models.Character

object FavoritesCacheMapper {
    fun toDomain(cache: List<FavoriteCharacterCache>): List<Character> =
        cache.map { favoritesCharactersCache ->
            favoritesCharactersCache.run {
                Character(
                    id = id,
                    name = name,
                    description = description,
                    imageUrl = imageUrl
                )
            }
        }

    fun toCached(character: Character): FavoriteCharacterCache =
        character.run {
            FavoriteCharacterCache(
                id = id,
                name = name,
                description = description,
                imageUrl = imageUrl
            )
        }
}
