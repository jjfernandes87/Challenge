package paixao.leonardo.marvel.heroes.lib.di

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.feature.network.RetrofitBuilder
import paixao.leonardo.marvel.heroes.feature.network.RetrofitBuilder.buildGateway
import paixao.leonardo.marvel.heroes.lib.CharactersInfrastructure
import paixao.leonardo.marvel.heroes.lib.MarvelGateway

object DataModule {
    private const val MODULE_NAME = "data"

    val injections = DI.Module(MODULE_NAME) {

        bind<CharacterService>() with provider {
            val api = RetrofitBuilder(okHttpClient = instance()).buildGateway<MarvelGateway>()
            CharactersInfrastructure(
                api = api
            )
        }
    }
}
