package br.com.mouzinho.data.repository.comics

import br.com.mouzinho.data.entity.ApiComicDetails
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.comic.ComicDetails
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.comics.ComicsRepository
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

class ComicsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper<ApiComicDetails, ComicDetails>
) : ComicsRepository {

    override fun getComicDetails(url: String): Single<ComicDetails> {
        return apiService.getComicDetails(url.replace("http:", "https:"))
            .map { response -> response.data.results.let(mapper::transform).firstOrNull() ?: throw Exception("Comics null") }
    }
}