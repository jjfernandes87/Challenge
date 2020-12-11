package br.com.mouzinho.data.repository.character

import androidx.paging.PositionalDataSource
import br.com.mouzinho.data.entity.character.ApiCharacterResponse
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class CharacterDataSource(
    private val apiService: ApiService,
    private val mapper: Mapper<ApiMarvelCharacter, MarvelCharacter>,
    private val query: String? = null
) : PositionalDataSource<MarvelCharacter>() {
    private val disposables = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MarvelCharacter>
    ) {
        charactersRequest(params.pageSize, params.requestedStartPosition)
            .subscribe { response ->
                callback.onResult(mapToCharacters(response), 0, response.data?.total ?: response.data?.count ?: 0)
            }
            .addTo(disposables)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MarvelCharacter>) {
        charactersRequest(params.loadSize, params.startPosition)
            .subscribe { response ->
                callback.onResult(mapToCharacters(response))
            }
            .addTo(disposables)
    }

    private fun mapToCharacters(response: ApiCharacterResponse) =
        response.data?.results?.map(mapper::transform) ?: emptyList()

    private fun charactersRequest(loadSize: Int, startPosition: Int): Observable<ApiCharacterResponse> {
        return if (query.isNullOrBlank()) apiService.getCharacters(loadSize, startPosition)
        else apiService.searchCharacters(loadSize, startPosition, query)
    }
}