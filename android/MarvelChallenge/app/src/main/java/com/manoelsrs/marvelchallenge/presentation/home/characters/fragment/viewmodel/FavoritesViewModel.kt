package com.manoelsrs.marvelchallenge.presentation.home.characters.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.actions.GetFavorites

class FavoritesViewModel(
    getFavorites: GetFavorites
) : ViewModel() {

    val characters: LiveData<PagedList<Character>> = getFavorites.execute()
}