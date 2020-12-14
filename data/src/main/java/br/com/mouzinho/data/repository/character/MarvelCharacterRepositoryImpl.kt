package br.com.mouzinho.data.repository.character

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.mouzinho.data.database.dao.FavoritesCharactersDao
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.character.MarvelCharacterLoadResult
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MarvelCharacterRepositoryImpl @Inject constructor(
    apiService: ApiService,
    characterMapper: Mapper<ApiMarvelCharacter, MarvelCharacter>,
    favoritesDao: FavoritesCharactersDao
) : MarvelCharacterRepository {
    private val pagingPublisher = PublishSubject.create<MarvelCharacterLoadResult>()
    private val pagingSource = CharacterDataSourceFactory(apiService, favoritesDao, characterMapper, pagingPublisher)

    override fun loadCharactersPagedList(pageSize: Int): Observable<MarvelCharacterLoadResult> {
        createPagingObservable(pageSize)
        return pagingPublisher.hide()
    }

    private fun createPagingObservable(pageSize: Int) = RxPagedListBuilder(
        pagingSource,
        PagedList.Config.Builder().setEnablePlaceholders(true).setPageSize(pageSize).build()
    )
        .buildObservable()
        .also { observable ->
            observable
                .map<MarvelCharacterLoadResult> {
                    MarvelCharacterLoadResult.Created(it)
                }
                .subscribe(pagingPublisher)
        }

    override fun sendSearchNameToPagingSource(name: String) {
        pagingSource.query = name
        pagingSource.invalidate()
    }

    override fun reload() {
        pagingSource.invalidate()
    }
}