package br.com.mouzinho.domain.repository.comics

import br.com.mouzinho.domain.entity.comic.ComicDetails
import io.reactivex.Single

interface ComicsRepository {

    fun getComicDetails(url: String): Single<ComicDetails>
}