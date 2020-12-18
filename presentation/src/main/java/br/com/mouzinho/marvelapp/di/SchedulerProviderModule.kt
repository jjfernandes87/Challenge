package br.com.mouzinho.marvelapp.di

import br.com.mouzinho.domain.scheduler.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
interface SchedulerProviderModule {

    @Binds
    fun bindSchedulerProvider(schedulerProviderImpl: DefaultSchedulerProvider) : SchedulerProvider
}