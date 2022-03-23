package paixao.leonardo.marvel.heroes.data.di

import androidx.room.Room
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import paixao.leonardo.marvel.heroes.data.CharactersInfrastructure
import paixao.leonardo.marvel.heroes.data.MarvelGateway
import paixao.leonardo.marvel.heroes.data.room.AppDatabase
import paixao.leonardo.marvel.heroes.data.room.FavoritesCharacterCacheDao
import paixao.leonardo.marvel.heroes.domain.services.CharacterService
import paixao.leonardo.marvel.heroes.feature.network.RetrofitBuilder
import paixao.leonardo.marvel.heroes.feature.network.RetrofitBuilder.buildGateway

object DataModule {
    private const val MODULE_NAME = "data"

    val injections = DI.Module(MODULE_NAME) {

        bind<AppDatabase>() with singleton {
            Room.databaseBuilder(
                instance(),
                AppDatabase::class.java, "database-name"
            ).build()
        }

        bind<FavoritesCharacterCacheDao>() with singleton {
            val database = instance<AppDatabase>()
            database.userDao()
        }

        bind<CharacterService>() with provider {
            val api = RetrofitBuilder(okHttpClient = instance()).buildGateway<MarvelGateway>()
            CharactersInfrastructure(
                api = api,
                favoritesDao = instance()
            )
        }
    }
}
