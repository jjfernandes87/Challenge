package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsActivity
import com.manoelsrs.marvelchallenge.presentation.home.details.DetailsPresenter
import dagger.Module
import dagger.Provides

@Module
class DetailsActivityModule {

    @PerActivity
    @Provides
    fun providesDetailsPresenter(activity: DetailsActivity) =
        DetailsPresenter(activity)
}