package paixao.leonardo.marvel.heroes

import android.app.Application
import android.content.Context
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton
import paixao.leonardo.marvel.heroes.data.di.DataModule
import paixao.leonardo.marvel.heroes.feature.character.di.CharacterModule
import paixao.leonardo.marvel.heroes.feature.network.di.NetworkingModule

class App : Application(), DIAware {
    override val di = DI {
        bind<Context>() with singleton {
            applicationContext
        }

        import(NetworkingModule.injections)
        import(DataModule.injections)
        import(CharacterModule.injections)
    }
}
