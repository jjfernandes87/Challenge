package br.com.mouzinho.data.repository.character

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.character.CharacterRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterMapper: Mapper<ApiMarvelCharacter, MarvelCharacter>
) : CharacterRepository {

    private val pagingPublisher = PublishSubject.create<PagedList<MarvelCharacter>>()
    private val pagingSource = CharacterPagingSource(apiService, characterMapper, pagingPublisher)

    override fun loadCharactersPagedList(pageSize: Int): Observable<PagedList<MarvelCharacter>> {
        createPagingObservable(pageSize)
        return pagingPublisher.hide()
    }

    private fun createPagingObservable(pageSize: Int) = RxPagedListBuilder(
        pagingSource,
        PagedList.Config.Builder().setEnablePlaceholders(true).setPageSize(pageSize).build()
    )
        .buildObservable()
        .also { observable -> observable.subscribe(pagingPublisher) }

    override fun sendSearchNameToPagingSource(name: String) {
        pagingSource.query = name
        pagingSource.invalidate()
    }
}