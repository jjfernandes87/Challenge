package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetFavorites
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.FavoritesViewModel
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class FavoritesFragmentModule {

    @PerFragment
    @Provides
    fun providesGetFavoritesAction(repository: Repository) =
        GetFavorites(repository)

    @PerFragment
    @Provides
    fun providesFavoritesViewModel(getFavorites: GetFavorites) =
        FavoritesViewModel(getFavorites)
}