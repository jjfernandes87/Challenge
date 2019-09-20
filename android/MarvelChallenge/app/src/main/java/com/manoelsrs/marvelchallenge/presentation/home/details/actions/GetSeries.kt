package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.presentation.home.details.producers.DataProducer
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class GetSeries(
    private val repository: Repository,
    private val dataProducer: DataProducer,
    private val saveSeries: SaveSeries
) {
    companion object {
        private const val LIMIT = 20
    }

    fun execute(characterId: Int): Single<List<Data>> {
        return repository.local.serie.deleteAll().toSingleDefault(characterId)
            .flatMap { repository.remote.characters.getSeries(it,LIMIT,0) }
            .map { dataProducer.produce(it) }
            .flatMap { saveSeries.execute(it) }
    }

    fun loadMore(characterId: Int): Single<List<Data>> {
        return repository.local.serie.getSeriesCount()
            .flatMap { repository.remote.characters.getSeries(characterId, LIMIT, it) }
            .map { dataProducer.produce(it) }
            .flatMap { saveSeries.execute(it) }
            .flatMap { repository.local.serie.getSeries() }
    }
}