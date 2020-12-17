package com.manoelsrs.marvelchallenge.di.component

import com.manoelsrs.marvelchallenge.MarvelApp
import com.manoelsrs.marvelchallenge.di.builder.ActivitiesBuilder
import com.manoelsrs.marvelchallenge.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivitiesBuilder::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MarvelApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: MarvelApp)
}