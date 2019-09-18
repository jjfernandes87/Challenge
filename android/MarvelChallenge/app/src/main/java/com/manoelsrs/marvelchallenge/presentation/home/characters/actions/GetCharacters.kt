package com.manoelsrs.marvelchallenge.presentation.home.characters.actions

import androidx.paging.Config
import androidx.paging.toLiveData
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.repository.Repository
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import io.reactivex.Single

class GetCharacters(
    private val repository: Repository,
    private val getOffset: GetOffset
) {

    companion object {
        private const val LIMIT = 20
    }

    private var offset = 0

    fun execute() = repository.local.character.getCharacters().toLiveData(
        Config(
            20,
            enablePlaceholders = true,
            maxSize = 60
        )
    )

    fun loadMoreItems(): Single<CharactersResponse> {
        return getOffset.execute()
            .flatMap { repository.remote.characters.getCharacters(LIMIT, it) }
            .doOnSuccess { response ->
                val character: List<Character> = response.data.results.map {
                    Character(it.id, it.name, it.thumbnail.path, it.thumbnail.extension)
                }
                repository.local.character.insert(character)
            }
    }
}