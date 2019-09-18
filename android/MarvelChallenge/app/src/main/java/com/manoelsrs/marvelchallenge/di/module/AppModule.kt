package com.manoelsrs.marvelchallenge.di.module

import android.app.Application
import androidx.room.Room
import com.manoelsrs.marvelchallenge.MarvelApp
import com.manoelsrs.marvelchallenge.repository.Repository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDto
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesApplication(app: MarvelApp): Application = app

    @Provides
    @Singleton
    fun providesRepository(charactersDatabase: CharactersDatabase): Repository =
        Repository(charactersDatabase)

    @Provides
    @Singleton
    fun providesCharactersDatabase(app: MarvelApp): CharactersDatabase = Room.databaseBuilder(
        app.applicationContext,
        CharactersDatabase::class.java,
        CharactersDto.TABLE
    ).fallbackToDestructiveMigration().build()
}