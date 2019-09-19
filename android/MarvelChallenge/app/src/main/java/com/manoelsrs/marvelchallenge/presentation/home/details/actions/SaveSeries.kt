package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class SaveSeries(private val repository: Repository) {

    fun execute(comics: List<Data>): Single<List<Data>> {
        return repository.local.serie.insert(comics).toSingleDefault(comics)
    }
}