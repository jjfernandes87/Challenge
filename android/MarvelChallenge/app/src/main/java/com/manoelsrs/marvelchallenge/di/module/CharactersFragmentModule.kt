package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewModel
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class CharactersFragmentModule {

    @PerFragment
    @Provides
    fun providesGetCharactersAction(repository: Repository) =
        GetCharacters(repository)

    @PerFragment
    @Provides
    fun providesCharacterViewModel(getCharacters: GetCharacters) =
        CharactersViewModel(getCharacters)
}