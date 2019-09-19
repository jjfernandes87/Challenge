package com.manoelsrs.marvelchallenge.presentation.home.characters.actions

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class SaveFavorite(private val repository: Repository) {

    fun execute(character: Character): Single<Character> {
        return repository.local.favorite.insert(character).toSingleDefault(character)
    }
}