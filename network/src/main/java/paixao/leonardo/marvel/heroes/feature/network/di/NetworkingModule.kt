package paixao.leonardo.marvel.heroes.feature.network.di

import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import paixao.leonardo.marvel.heroes.feature.network.RetrofitBuilder
import paixao.leonardo.marvel.heroes.feature.network.auth.AuthorizationProvider
import retrofit2.Retrofit
import java.util.Date

class NetworkingModule {
    val injections = DI.Module(name = MODULE_NAME) {

        bind<OkHttpClient>() with singleton {
            OkHttpClient
                .Builder()
                .build()
        }

        bind<Retrofit>() with provider {
            RetrofitBuilder.invoke(okHttpClient = instance())
        }

        bind<AuthorizationProvider>() with provider {
            AuthorizationProvider(date = Date())
        }
    }

    companion object {
        private const val MODULE_NAME = "networking"
    }
}
