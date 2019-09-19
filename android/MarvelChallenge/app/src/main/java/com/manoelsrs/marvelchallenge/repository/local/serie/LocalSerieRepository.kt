package com.manoelsrs.marvelchallenge.repository.local.serie

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.local.serie.entity.SerieDatabase
import com.manoelsrs.marvelchallenge.repository.local.serie.mappers.SerieMapper
import com.manoelsrs.marvelchallenge.repository.local.serie.resources.LocalSerieResources
import io.reactivex.Completable
import io.reactivex.Single

class LocalSerieRepository(private val database: SerieDatabase) : LocalSerieResources {

    override fun getSeriesCount(): Single<Int> {
        return database.serieDao().getSeriesCount()
    }

    override fun getSeries(): Single<List<Data>> {
        return database.serieDao().getSeries()
            .map { SerieMapper.toData(it) }
    }

    override fun insert(series: List<Data>): Completable = Completable.fromCallable {
        database.serieDao().insert(SerieMapper.toSeriesDto(series))
    }

    override fun deleteAll(): Completable = Completable.fromCallable {
        database.serieDao().deleteAll()
    }
}