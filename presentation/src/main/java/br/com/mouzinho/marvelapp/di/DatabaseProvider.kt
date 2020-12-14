package br.com.mouzinho.marvelapp.di

import android.content.Context
import br.com.mouzinho.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseProvider {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) = AppDatabase.build(context)

    @Provides
    @Singleton
    fun providesFavoritesCharactersDao(database: AppDatabase) = database.favoritesCharactersDao()
}