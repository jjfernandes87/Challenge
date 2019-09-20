package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.presentation.home.details.producers.DataProducer
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class GetComics(
    private val repository: Repository,
    private val dataProducer: DataProducer,
    private val saveComics: SaveComics
) {
    companion object {
        private const val LIMIT = 20
    }

    fun execute(characterId: Int): Single<List<Data>> {
        return repository.local.comic.deleteAll().toSingleDefault(characterId)
            .flatMap { repository.remote.characters.getComics(it,LIMIT,0) }
            .map { dataProducer.produce(it) }
            .flatMap { saveComics.execute(it) }
    }

    fun loadMore(characterId: Int): Single<List<Data>> {
        return repository.local.comic.getComicsCount()
            .flatMap { repository.remote.characters.getComics(characterId, LIMIT, it) }
            .map { dataProducer.produce(it) }
            .flatMap { saveComics.execute(it) }
            .flatMap { repository.local.comic.getComics().toSingle() }
    }
}