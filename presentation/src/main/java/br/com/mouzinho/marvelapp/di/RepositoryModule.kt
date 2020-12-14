package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.data.repository.character.MarvelCharacterRepositoryImpl
import br.com.mouzinho.data.repository.favorite.FavoritesMarvelCharacterRepositoryImpl
import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import br.com.mouzinho.domain.repository.favorite.FavoritesMarvelCharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCharacterRepository(impl: MarvelCharacterRepositoryImpl): MarvelCharacterRepository

    @Binds
    fun bindFavoritRepository(impl: FavoritesMarvelCharacterRepositoryImpl): FavoritesMarvelCharacterRepository
}