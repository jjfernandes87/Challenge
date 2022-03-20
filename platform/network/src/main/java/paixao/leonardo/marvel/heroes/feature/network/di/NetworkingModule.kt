package paixao.leonardo.marvel.heroes.feature.network.di

import okhttp3.OkHttpClient
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import paixao.leonardo.marvel.heroes.feature.network.RetrofitBuilder
import paixao.leonardo.marvel.heroes.feature.network.auth.AuthorizationInterceptor
import paixao.leonardo.marvel.heroes.feature.network.auth.AuthorizationProvider
import retrofit2.Retrofit
import java.util.Date

object NetworkingModule {
    private const val MODULE_NAME = "networking"

    val injections = DI.Module(name = MODULE_NAME) {

        bind<AuthorizationProvider>() with provider {
            AuthorizationProvider(date = Date())
        }

        bind<AuthorizationInterceptor>() with provider {
            AuthorizationInterceptor(authorizationProvider = instance())
        }

        bind<OkHttpClient>() with singleton {
            val authInterceptor = instance<AuthorizationInterceptor>()
            OkHttpClient
                .Builder()
                .addInterceptor(authInterceptor)
                .build()
        }

        bind<Retrofit>() with singleton {
            RetrofitBuilder.invoke(okHttpClient = instance())
        }
    }
}
