package com.manoelsrs.marvelchallenge.presentation.home.characters.actions

import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class GetOffset(private val repository: Repository) {

    fun execute(): Single<Int> {
        return repository.local.character.getCharactersCount()
    }
}