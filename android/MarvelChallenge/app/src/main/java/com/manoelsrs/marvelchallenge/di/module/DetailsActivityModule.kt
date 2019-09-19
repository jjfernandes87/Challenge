package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsPresenter
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.GetComics
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.GetSeries
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.SaveComics
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.SaveSeries
import com.manoelsrs.marvelchallenge.presentation.home.details.producers.DataProducer
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {

    @PerActivity
    @Provides
    fun providesDataProducer() = DataProducer()

    @PerActivity
    @Provides
    fun providesSaveComics(repository: Repository) = SaveComics(repository)

    @PerActivity
    @Provides
    fun providesSaveSeries(repository: Repository) = SaveSeries(repository)

    @PerActivity
    @Provides
    fun providesGetComicsAction(
        repository: Repository,
        dataProducer: DataProducer,
        saveComics: SaveComics
    ) = GetComics(repository, dataProducer, saveComics)

    @PerActivity
    @Provides
    fun providesGetSeriesAction(
        repository: Repository,
        dataProducer: DataProducer,
        saveSeries: SaveSeries
    ) = GetSeries(repository, dataProducer, saveSeries)

    @PerActivity
    @Provides
    fun providesDetailsPresenter(
        activity: DetailsActivity,
        getComics: GetComics,
        getSeries: GetSeries
    ) =
        DetailsPresenter(activity, getComics, getSeries)
}