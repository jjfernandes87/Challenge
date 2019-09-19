package com.manoelsrs.marvelchallenge.presentation.home.characters.actions

import androidx.paging.Config
import androidx.paging.toLiveData
import com.manoelsrs.marvelchallenge.model.Character
import com.manoelsrs.marvelchallenge.presentation.home.characters.producers.CharacterProducer
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

    fun execute() = repository.local.character.getCharacters().toLiveData(
        Config(
            20,
            enablePlaceholders = true,
            maxSize = 60
        )
    )

    fun loadMoreItems(content: String): Single<CharactersResponse> {
        return if (content.isBlank()) {
            getOffset.execute()
                .flatMap { repository.remote.characters.getCharacters(LIMIT, it) }
                .doOnSuccess { saveCharacters(it) }
        } else {
            getOffset.execute()
                .flatMap { repository.remote.characters.getCharacters(LIMIT, it, content) }
                .doOnSuccess { saveCharacters(it) }
        }
    }

    fun updateItems(content: String): Single<CharactersResponse> {
        return if (content.isBlank()) {
            repository.local.character.deleteAll().toSingleDefault(Unit)
                .flatMap { repository.remote.characters.getCharacters(LIMIT, 0) }
                .doOnSuccess { saveCharacters(it) }
        } else {
            repository.local.character.deleteAll().toSingleDefault(Unit)
                .flatMap { repository.remote.characters.getCharacters(LIMIT, 0, content) }
                .doOnSuccess { saveCharacters(it) }
        }
    }

    private fun saveCharacters(response: CharactersResponse) {
        val character: List<Character> = response.data.results.map { CharacterProducer.execute(it) }
        repository.local.character.insert(character)
    }
}