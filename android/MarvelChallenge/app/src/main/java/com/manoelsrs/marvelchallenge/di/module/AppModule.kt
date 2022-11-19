package com.manoelsrs.marvelchallenge.di.module

import android.app.Application
import androidx.room.Room
import com.manoelsrs.marvelchallenge.MarvelApp
import com.manoelsrs.marvelchallenge.repository.Repository
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDatabase
import com.manoelsrs.marvelchallenge.repository.local.character.entity.CharactersDto
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDatabase
import com.manoelsrs.marvelchallenge.repository.local.comic.entity.ComicDto
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDatabase
import com.manoelsrs.marvelchallenge.repository.local.favorite.entity.FavoriteDto
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDatabase
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDto
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesApplication(app: MarvelApp): Application = app

    @Provides
    @Singleton
    fun providesRepository(
        charactersDatabase: CharactersDatabase,
        comicDatabase: ComicDatabase,
        favoriteDatabase: FavoriteDatabase,
        serieDatabase: SerieDatabase
    ): Repository =
        Repository(charactersDatabase, comicDatabase, favoriteDatabase, serieDatabase)

    @Provides
    @Singleton
    fun providesSubject(): BehaviorSubject<String> = BehaviorSubject.create<String>()

    @Provides
    @Singleton
    fun providesCharactersDatabase(app: MarvelApp): CharactersDatabase = Room.databaseBuilder(
        app.applicationContext,
        CharactersDatabase::class.java,
        CharactersDto.TABLE
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesFavoriteDatabase(app: MarvelApp): FavoriteDatabase = Room.databaseBuilder(
        app.applicationContext,
        FavoriteDatabase::class.java,
        FavoriteDto.TABLE
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesComicDatabase(app: MarvelApp): ComicDatabase = Room.databaseBuilder(
        app.applicationContext,
        ComicDatabase::class.java,
        ComicDto.TABLE
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesSerieDatabase(app: MarvelApp): SerieDatabase = Room.databaseBuilder(
        app.applicationContext,
        SerieDatabase::class.java,
        SerieDto.TABLE
    ).fallbackToDestructiveMigration().build()
}