package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.data.repository.character.CharacterRepositoryImpl
import br.com.mouzinho.domain.repository.character.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}