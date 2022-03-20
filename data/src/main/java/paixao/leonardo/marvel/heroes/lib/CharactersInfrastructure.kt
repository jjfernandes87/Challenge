package paixao.leonardo.marvel.heroes.lib

import paixao.leonardo.marvel.heroes.domain.models.Character
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.feature.core.utils.request

class CharactersInfrastructure(private val api: MarvelGateway) : CharacterService {
    override suspend fun retrieveCharacter(): List<Character> = request {
        val response = api.getCharacters()
        CharactersMapper.toDomain(response)
    }
}
