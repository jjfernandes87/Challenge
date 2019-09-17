package com.manoelsrs.marvelchallenge.presentation.home.characters.actions

import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class GetCharacters(private val repository: Repository) {

    companion object {
        private const val LIMIT = 20
    }

    fun execute(offset: Int): Single<List<Character>> {
        return repository.remote.characters.getCharacters(limit = LIMIT, offset = offset)
            .map { it.data.results }
            .map { results ->
                results.map { Character(
                    id = it.id,
                    name = it.name,
                    photo = it.thumbnail.path,
                    photoExtension = it.thumbnail.extension)
                }
            }
    }
}