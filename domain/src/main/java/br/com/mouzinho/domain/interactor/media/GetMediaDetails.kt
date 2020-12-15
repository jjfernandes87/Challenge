package br.com.mouzinho.domain.interactor.media

import br.com.mouzinho.domain.repository.comics.MediaRepository
import javax.inject.Inject

class GetMediaDetails @Inject constructor(
    private val repository: MediaRepository
) {

    operator fun invoke(url: String) = repository.getMediaDetails(url)
}