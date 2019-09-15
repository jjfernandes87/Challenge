package com.manoelsrs.marvelchallenge.di.module

import android.app.Application
import com.manoelsrs.marvelchallenge.MarvelApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesApplication(app: MarvelApp): Application = app
}