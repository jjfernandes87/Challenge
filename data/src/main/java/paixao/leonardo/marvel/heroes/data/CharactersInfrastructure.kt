package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.room.FavoritesCacheMapper
import paixao.leonardo.marvel.heroes.data.room.FavoritesCacheMapper.toCached
import paixao.leonardo.marvel.heroes.data.room.FavoritesCharacterCacheDao
import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.domain.models.PagingStatus
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.domain.services.FavoriteCharacterService
import paixao.leonardo.marvel.heroes.feature.core.utils.request

class CharactersInfrastructure(
    private val api: MarvelGateway,
    private val favoritesDao: FavoritesCharacterCacheDao,
    private val pagingHandler: PagingHandler
) : CharacterService, FavoriteCharacterService {
    override suspend fun retrieveCharacters(isRefreshing: Boolean): List<MarvelCharacter> =
        request {
            val pagingStatus = pagingHandler.retrievePageStatus(isRefreshing)

            val queryParams = when (pagingStatus) {
                is PagingStatus.Find -> pagingStatus.value
                is PagingStatus.Refresh -> pagingStatus.value
                PagingStatus.END -> null
            }

            if (queryParams != null) {
                val response = api.getCharacters(queryParams)
                pagingHandler.updatePagingHandler(response.data)
                CharactersMapper.toDomain(response)
            } else
                emptyList()
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
