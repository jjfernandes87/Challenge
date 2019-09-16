package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersPresenter
import dagger.Module
import dagger.Provides

@Module
class CharactersActivityModule {

    @PerActivity
    @Provides
    fun providesCharactersPresenter(activity: CharactersActivity) =
        CharactersPresenter(activity)
}