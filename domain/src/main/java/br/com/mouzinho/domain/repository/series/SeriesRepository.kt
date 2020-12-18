package br.com.mouzinho.domain.repository.series

import br.com.mouzinho.domain.entity.comic.MediaDetails
import io.reactivex.Single

interface SeriesRepository {

    fun getSerieDetails(url: String): Single<MediaDetails>
}