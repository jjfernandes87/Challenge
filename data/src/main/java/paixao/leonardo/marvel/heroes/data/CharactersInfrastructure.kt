package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.room.FavoritesCacheMapper
import paixao.leonardo.marvel.heroes.data.room.FavoritesCacheMapper.toCached
import paixao.leonardo.marvel.heroes.data.room.FavoritesCharacterCacheDao
import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.feature.core.utils.request

class CharactersInfrastructure(
    private val api: MarvelGateway,
    private val favoritesDao: FavoritesCharacterCacheDao
) : CharacterService {
    override suspend fun retrieveCharacter(): List<Character> = request {
        val response = api.getCharacters()
        CharactersMapper.toDomain(response)
    }

    override suspend fun retrieveFavoriteCharacters(): List<Character> =
        request {
            val cachedFavoritesChar = favoritesDao.getAll()
            FavoritesCacheMapper.toDomain(cachedFavoritesChar)
        }

    override suspend fun saveFavoriteCharacter(character: Character) {
        request {
            val cachedCharacter = toCached(character)
            favoritesDao.insert(cachedCharacter)
        }
    }

    override suspend fun removeFavoriteCharacter(character: Character) =
        request {
            val cachedCharacter = toCached(character)
            favoritesDao.delete(cachedCharacter)
        }
}
