package br.com.mouzinho.data.repository.character

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.character.CharacterRepository
import io.reactivex.Observable
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterMapper: Mapper<ApiMarvelCharacter, MarvelCharacter>
) : CharacterRepository {

    private val pagingSource by lazy { CharacterPagingSource(apiService, characterMapper) }

    override fun loadCharactersPagedList(pageSize: Int): Observable<PagedList<MarvelCharacter>> {
        return RxPagedListBuilder(
            pagingSource,
            PagedList.Config.Builder().setEnablePlaceholders(true).setPageSize(pageSize).build()
        )
            .buildObservable()
    }

    override fun sendSearchNameToPagingSource(name: String) {
        pagingSource.query = name
        pagingSource.invalidate()
    }
}