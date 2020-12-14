package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.domain.resources.StringResources
import br.com.mouzinho.marvelapp.resources.StringResourcesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface ResourcesModule {

    @Binds
    fun bindStringResource(impl: StringResourcesImpl): StringResources
}