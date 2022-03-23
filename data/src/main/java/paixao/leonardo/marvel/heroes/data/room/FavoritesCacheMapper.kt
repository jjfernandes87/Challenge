package paixao.leonardo.marvel.heroes.data.room

import paixao.leonardo.marvel.heroes.data.room.models.FavoriteCharacterCache
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter

object FavoritesCacheMapper {
    fun toDomain(cache: List<FavoriteCharacterCache>): List<MarvelCharacter> =
        cache.map { favoritesCharactersCache ->
            favoritesCharactersCache.run {
                MarvelCharacter(
                    id = id,
                    name = name,
                    description = description,
                    imageUrl = imageUrl,
                    isFavorite = true
                )
            }
        }

    fun toCached(character: MarvelCharacter): FavoriteCharacterCache =
        character.run {
            FavoriteCharacterCache(
                id = id,
                name = name,
                description = description,
                imageUrl = imageUrl
            )
        }
}
