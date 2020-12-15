package br.com.mouzinho.domain.interactor.comics

import br.com.mouzinho.domain.repository.comics.ComicsRepository
import javax.inject.Inject

class GetComicDetails @Inject constructor(
    private val repository: ComicsRepository
) {

    operator fun invoke(url: String) = repository.getComicDetails(url)
}