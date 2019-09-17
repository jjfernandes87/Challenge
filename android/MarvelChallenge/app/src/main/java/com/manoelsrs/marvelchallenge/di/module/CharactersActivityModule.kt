package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersPresenter
import com.manoelsrs.marvelchallenge.presentation.home.characters.adapter.TabAdapter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.CharactersFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.FavoritesFragment
import dagger.Module
import dagger.Provides

@Module
class CharactersActivityModule {

    @PerActivity
    @Provides
    fun providesCharactersPresenter(activity: CharactersActivity) =
        CharactersPresenter(activity)

    @PerActivity
    @Provides
    fun providesTabAdapter(activity: CharactersActivity) =
        TabAdapter(activity, CharactersFragment(), FavoritesFragment())
}