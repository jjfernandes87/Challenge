package paixao.leonardo.marvel.heroes.feature.character

import paixao.leonardo.marvel.heroes.domain.models.MarvelCharacter
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.domain.services.CharactersHandler
import paixao.leonardo.marvel.heroes.domain.services.FavoriteCharacterService

internal class CharactersAgent(
    private val characterService: CharacterService,
    private val favoriteCharacterService: FavoriteCharacterService
) : CharactersHandler {
    override suspend fun retrieveCharacters(): List<MarvelCharacter> {
        val favoriteCharacters = favoriteCharacterService.retrieveFavoriteCharacters()
        val incomingCharacters = characterService.retrieveCharacters()

        val settledFavoriteList = incomingCharacters.map { character ->
            val isFavoriteCharacter = favoriteCharacters.any { it.id == character.id }
            character.copy(isFavorite = isFavoriteCharacter)
        }

        return settledFavoriteList
    }

}