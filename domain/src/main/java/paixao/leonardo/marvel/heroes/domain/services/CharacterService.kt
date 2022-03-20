package paixao.leonardo.marvel.heroes.domain.services

import paixao.leonardo.marvel.heroes.domain.models.Character

interface CharacterService {
    suspend fun retrieveCharacter(): List<Character>
}
