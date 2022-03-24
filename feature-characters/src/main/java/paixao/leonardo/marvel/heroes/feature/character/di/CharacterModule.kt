package paixao.leonardo.marvel.heroes.feature.character.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import paixao.leonardo.marvel.heroes.domain.services.CharactersHandler
import paixao.leonardo.marvel.heroes.feature.character.screens.details.CharacterDetailsViewModel
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.CharacterViewModel
import paixao.leonardo.marvel.heroes.feature.character.screens.listing.CharactersAgent

object CharacterModule {
    private const val MODULE_NAME = "feature-character-module"

    val injections = DI.Module(MODULE_NAME) {

        bind<CharacterViewModel>() with provider {
            CharacterViewModel(
                charactersHandler = instance(),
                favoriteCharacterService = instance()
            )
        }

        bind<CharactersHandler>() with provider {
            CharactersAgent(
                characterService = instance(),
                favoriteCharacterService = instance()
            )
        }

        bind<CharacterDetailsViewModel>() with provider {
            CharacterDetailsViewModel(
                favoriteCharacterService = instance()
            )
        }

    }
}