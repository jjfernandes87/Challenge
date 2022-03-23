package paixao.leonardo.marvel.heroes.domain.services

import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter

interface FavoriteCharacterService {
    suspend fun retrieveFavoriteCharacters(): List<MarvelCharacter>

    suspend fun saveFavoriteCharacter(character: MarvelCharacter): Unit

    suspend fun removeFavoriteCharacter(character: MarvelCharacter): Unit
}