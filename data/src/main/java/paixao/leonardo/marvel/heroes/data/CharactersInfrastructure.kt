package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.room.FavoritesCacheMapper
import paixao.leonardo.marvel.heroes.data.room.FavoritesCacheMapper.toCached
import paixao.leonardo.marvel.heroes.data.room.FavoritesCharacterCacheDao
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.domain.services.FavoriteCharacterService
import paixao.leonardo.marvel.heroes.feature.core.utils.request

class CharactersInfrastructure(
    private val api: MarvelGateway,
    private val favoritesDao: FavoritesCharacterCacheDao
) : CharacterService, FavoriteCharacterService {
    override suspend fun retrieveCharacters(): List<MarvelCharacter> = request {
        val response = api.getCharacters()
        CharactersMapper.toDomain(response)
    }

    override suspend fun retrieveFavoriteCharacters(): List<MarvelCharacter> =
        request {
            val cachedFavoritesChar = favoritesDao.getAll()
            FavoritesCacheMapper.toDomain(cachedFavoritesChar)
        }

    override suspend fun saveFavoriteCharacter(character: MarvelCharacter) {
        request {
            val cachedCharacter = toCached(character)
            favoritesDao.insert(cachedCharacter)
        }
    }

    override suspend fun removeFavoriteCharacter(character: MarvelCharacter) =
        request {
            val cachedCharacter = toCached(character)
            favoritesDao.delete(cachedCharacter)
        }
}
