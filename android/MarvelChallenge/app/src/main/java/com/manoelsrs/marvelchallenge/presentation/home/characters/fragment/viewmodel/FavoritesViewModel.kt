package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetFavorites


class FavoritesViewModel(
    private val getFavorites: GetFavorites
) : ViewModel() {

    private val viewState: MutableLiveData<CharactersViewState> = MutableLiveData()
    var characters: LiveData<PagedList<Character>> = getFavorites.execute()

    fun viewState(): LiveData<CharactersViewState> = viewState

    fun updateItems(content: String) {
        if (content.isBlank()) characters = getFavorites.execute()
        characters = getFavorites.execute(content)
    }
}