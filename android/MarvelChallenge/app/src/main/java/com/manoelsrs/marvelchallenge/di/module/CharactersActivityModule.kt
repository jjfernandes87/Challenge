package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersActivity
import com.manoelsrs.marvelchallenge.presentation.home.characters.CharactersPresenter
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.CharactersFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.FavoritesFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.adapter.TabAdapter
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject

@Module
class CharactersActivityModule {

    @PerActivity
    @Provides
    fun providesCharactersPresenter(
        activity: CharactersActivity,
        subject: BehaviorSubject<String>
    ) =
        CharactersPresenter(activity, subject)

    @PerActivity
    @Provides
    fun providesTabAdapter(activity: CharactersActivity) =
        TabAdapter(activity, CharactersFragment(), FavoritesFragment())
}