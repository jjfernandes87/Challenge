package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetOffset
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewModel
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class CharactersFragmentModule {

    @PerFragment
    @Provides
    fun providesGetOffsetAction(repository: Repository) =
        GetOffset(repository)

    @PerFragment
    @Provides
    fun providesGetCharactersAction(repository: Repository, getOffset: GetOffset) =
        GetCharacters(repository, getOffset)

    @PerFragment
    @Provides
    fun providesCharacterViewModel(getCharacters: GetCharacters) =
        CharactersViewModel(getCharacters)
}