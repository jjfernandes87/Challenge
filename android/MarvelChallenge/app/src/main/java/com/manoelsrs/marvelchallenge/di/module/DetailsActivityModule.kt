package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsPresenter
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.*
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
    fun providesSaveFavoriteAction(repository: Repository) =
        SaveFavorite(repository)

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
    fun providesCheckFavoriteAction(repository: Repository) =
        CheckFavorite(repository)

    @PerActivity
    @Provides
    fun providesDeleteFavoriteAction(repository: Repository) =
        DeleteFavorite(repository)

    @PerActivity
    @Provides
    fun providesDetailsPresenter(
        activity: DetailsActivity,
        getComics: GetComics,
        getSeries: GetSeries,
        saveFavorite: SaveFavorite,
        checkFavorite: CheckFavorite,
        deleteFavorite: DeleteFavorite
    ) = DetailsPresenter(
        activity,
        getComics,
        getSeries,
        saveFavorite,
        checkFavorite,
        deleteFavorite
    )
}