package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

sealed class CharactersViewState {
    data class Error(val error: String) : CharactersViewState()
    data class Loading(val isLoading: Boolean) : CharactersViewState()
}