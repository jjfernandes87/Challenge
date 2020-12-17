package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class CheckFavorite(private val repository: Repository) {

    fun execute(character: Character): Single<Boolean> {
        return repository.local.favorite.getFavoritesSingle()
            .map { it.contains(character) }
    }
}