package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class DeleteFavorite(private val repository: Repository) {

    fun execute(character: Character): Single<Character> {
        return repository.local.favorite.delete(character).toSingleDefault(character)
    }
}