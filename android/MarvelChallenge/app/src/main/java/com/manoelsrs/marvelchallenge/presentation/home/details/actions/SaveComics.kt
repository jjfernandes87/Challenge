package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class SaveComics(private val repository: Repository) {

    fun execute(comics: List<Data>): Single<List<Data>> {
        return repository.local.comic.insert(comics).toSingleDefault(comics)
    }
}