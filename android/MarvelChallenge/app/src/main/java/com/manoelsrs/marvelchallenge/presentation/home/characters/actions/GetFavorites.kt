package com.manoelsrs.marvelchallenge.presentation.home.characters.actions

import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository

class GetFavorites(private val repository: Repository) {

    fun execute(): LiveData<PagedList<Character>> {
        return repository.local.favorite.getFavorites().toLiveData(
            Config(
                20,
                enablePlaceholders = true,
                maxSize = 60
            )
        )
    }

    fun execute(search: String): LiveData<PagedList<Character>> {
        return repository.local.favorite.getFavorites(search.toUpperCase()).toLiveData(
            Config(
                20,
                enablePlaceholders = true,
                maxSize = 60
            )
        )
    }
}