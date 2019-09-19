package com.manoelsrs.marvelchallenge.presentation.home.details.actions

import com.manoelsrs.marvelchallenge.model.Data
import com.manoelsrs.marvelchallenge.repository.Repository
import io.reactivex.Single

class GetComics(private val repository: Repository) {

    fun execute(characterId: Int): Single<List<Data>> {
        return repository.remote.characters.getComics(characterId,20,0)
            .map {response ->
                response.data.results.map {
                    Data(
                        id = it.id,
                        title = it.title,
                        photo = it.thumbnail?.path ?: "",
                        photoExtension = it.thumbnail?.extension ?: ""
                    )
                }
            }
    }
}