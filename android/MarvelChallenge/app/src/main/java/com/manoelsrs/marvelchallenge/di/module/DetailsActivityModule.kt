package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsPresenter
import com.manoelsrs.marvelchallenge.presentation.home.details.actions.GetComics
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {

    @PerActivity
    @Provides
    fun providesGetComicsAction(repository: Repository) =
        GetComics(repository)

    @PerActivity
    @Provides
    fun providesDetailsPresenter(activity: DetailsActivity, getComics: GetComics) =
        DetailsPresenter(activity, getComics)
}