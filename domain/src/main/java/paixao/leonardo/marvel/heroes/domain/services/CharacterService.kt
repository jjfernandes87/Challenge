package paixao.leonardo.marvel.heroes.domain.services

import paixao.leonardo.marvel.heroes.domain.models.Character

interface CharacterService {
    suspend fun retrieveCharacter(): List<Character>

    suspend fun retrieveFavoriteCharacters(): List<Character>

    suspend fun saveFavoriteCharacter(character: Character): Unit

    suspend fun removeFavoriteCharacter(character: Character): Unit
}
