package com.manoelsrs.marvelchallenge.repository.local.serie.resources

import com.manoelsrs.marvelchallenge.model.Data
import io.reactivex.Completable
import io.reactivex.Single

interface LocalSerieResources {
    fun getSeriesCount(): Single<Int>
    fun getSeries(): Single<List<Data>>
    fun insert(series: List<Data>): Completable
    fun deleteAll(): Completable
}