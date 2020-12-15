package br.com.mouzinho.domain.repository.comics

import br.com.mouzinho.domain.entity.comic.MediaDetails
import io.reactivex.Single

interface MediaRepository {

    fun getMediaDetails(url: String): Single<MediaDetails>
}