package paixao.leonardo.marvel.heroes.domain.services

import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter

interface CharactersHandler {
    suspend fun retrieveCharacters(): List<MarvelCharacter>
}