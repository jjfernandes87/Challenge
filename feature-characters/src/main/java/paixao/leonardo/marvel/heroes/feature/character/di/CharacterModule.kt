package paixao.leonardo.marvel.heroes.feature.character.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import paixao.leonardo.marvel.heroes.feature.character.CharacterViewModel

object CharacterModule {
    private const val MODULE_NAME = "feature-character-module"

    val injections = DI.Module(MODULE_NAME) {

        bind<CharacterViewModel>() with provider {
            CharacterViewModel(
                characterService = instance()
            )
        }

    }
}