package br.com.mouzinho.data.repository.character

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import br.com.mouzinho.data.database.dao.FavoritesCharactersDao
import br.com.mouzinho.data.entity.character.ApiCharacterResponse
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.errorHandler.ErrorHandler.toAppError
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class CharacterDataSource(
    private val apiService: ApiService,
    private val favoritesDao: FavoritesCharactersDao,
    private val mapper: Mapper<ApiMarvelCharacter, MarvelCharacter>,
    private val pagingPublisher: PublishSubject<PagedList<MarvelCharacter>>,
    private val query: String? = null
) : PositionalDataSource<MarvelCharacter>() {
    private val disposables = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<MarvelCharacter>
    ) {
        charactersRequest(params.pageSize, params.requestedStartPosition)
            .subscribeBy(
                onNext = { response ->
                    callback.onResult(
                        matchWithFavorites(mapToMarvelCharacters(response)),
                        0,
                        response.data?.total ?: response.data?.count ?: 0
                    )
                },
                onError = { pagingPublisher.onError(it.toAppError()) }
            )
            .addTo(disposables)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MarvelCharacter>) {
        charactersRequest(params.loadSize, params.startPosition)
            .subscribeBy(
                onNext = { response -> callback.onResult(matchWithFavorites(mapToMarvelCharacters(response))) },
                onError = { pagingPublisher.onError(it.toAppError()) }
            )
            .addTo(disposables)
    }

    private fun matchWithFavorites(list: List<MarvelCharacter>): List<MarvelCharacter> {
        return list.map {
            it.copy(isFavorite = favoritesDao.getById(it.id).isNotEmpty())
        }
    }

    private fun mapToMarvelCharacters(response: ApiCharacterResponse) =
        response.data?.results?.map(mapper::transform) ?: emptyList()

    private fun charactersRequest(loadSize: Int, startPosition: Int): Observable<ApiCharacterResponse> {
        return if (query.isNullOrBlank()) apiService.getCharacters(loadSize, startPosition)
        else apiService.searchCharacters(loadSize, startPosition, query)
    }
}