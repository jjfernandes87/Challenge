package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.CharactersFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.CharactersFragmentPresenter
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class CharactersFragmentModule {

    @PerFragment
    @Provides
    fun providesCharactersFragmentPresenter(
        fragment: CharactersFragment,
        getCharacters: GetCharacters
    ) =
        CharactersFragmentPresenter(fragment, getCharacters)

    @PerFragment
    @Provides
    fun providesGetCharactersAction(repository: Repository) = GetCharacters(repository)
}