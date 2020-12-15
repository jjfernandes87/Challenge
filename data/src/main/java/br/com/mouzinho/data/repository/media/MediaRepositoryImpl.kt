package br.com.mouzinho.data.repository.media

import br.com.mouzinho.data.entity.ApiMediaDetails
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.data.utils.httpToHttps
import br.com.mouzinho.domain.entity.comic.MediaDetails
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.comics.MediaRepository
import io.reactivex.Single
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper<ApiMediaDetails, MediaDetails>
) : MediaRepository {

    override fun getMediaDetails(url: String): Single<MediaDetails> {
        return apiService.getComicDetails(url.httpToHttps())
            .map { response -> response.data.results.let(mapper::transform).firstOrNull() ?: throw Exception("Comics null") }
    }
}