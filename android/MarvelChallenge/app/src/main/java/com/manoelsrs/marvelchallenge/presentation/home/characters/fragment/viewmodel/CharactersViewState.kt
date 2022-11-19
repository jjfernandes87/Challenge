package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

sealed class CharactersViewState {
    data class Error(val error: Throwable) : CharactersViewState()
    data class Loading(val isLoading: Boolean) : CharactersViewState()
}