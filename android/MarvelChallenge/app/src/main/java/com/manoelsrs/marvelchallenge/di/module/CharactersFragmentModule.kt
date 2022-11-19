package com.manoelsrs.marvelchallenge.di.module

import com.manoelsrs.marvelchallenge.di.PerFragment
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetCharacters
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetOffset
import com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel.CharactersViewModel
import com.manoelsrs.marvelchallenge.presentation.home.characters.producers.CharacterProducer
import com.manoelsrs.marvelchallenge.repository.Repository
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@Module
class CharactersFragmentModule {

    @PerFragment
    @Provides
    fun providesGetOffsetAction(repository: Repository) =
        GetOffset(repository)

    @PerFragment
    @Provides
    fun providesCharacterProducer() = CharacterProducer()

    @PerFragment
    @Provides
    fun providesGetCharactersAction(
        repository: Repository,
        getOffset: GetOffset,
        characterProducer: CharacterProducer
    ) = GetCharacters(repository, getOffset, characterProducer)

    @PerFragment
    @Provides
    fun providesCharacterViewModel(getCharacters: GetCharacters) =
        CharactersViewModel(getCharacters)

    @PerFragment
    @Provides
    fun providesSearchListener(subject: BehaviorSubject<String>): Observable<String> = subject
}