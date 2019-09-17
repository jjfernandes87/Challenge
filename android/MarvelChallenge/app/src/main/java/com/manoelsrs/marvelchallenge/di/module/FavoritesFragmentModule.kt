package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.FavoritesFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.FavoritesFragmentPresenter
import dagger.Module
import dagger.Provides

@Module
class FavoritesFragmentModule {

    @PerFragment
    @Provides
    fun providesCharactersFragmentPresenter(fragment: FavoritesFragment) =
        FavoritesFragmentPresenter(fragment)
}