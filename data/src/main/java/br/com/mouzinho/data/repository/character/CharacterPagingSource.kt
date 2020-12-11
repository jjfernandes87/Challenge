package br.com.mouzinho.data.repository.character

import androidx.paging.DataSource
import androidx.paging.PagedList
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import io.reactivex.subjects.PublishSubject

class CharacterPagingSource(
    private val apiService: ApiService,
    private val mapper: Mapper<ApiMarvelCharacter, MarvelCharacter>,
    private val pagingPublisher: PublishSubject<PagedList<MarvelCharacter>>
) : DataSource.Factory<Int, MarvelCharacter>() {
    var query = ""
    private lateinit var dataSource: DataSource<Int, MarvelCharacter>

    fun invalidate() = dataSource.invalidate()

    override fun create(): DataSource<Int, MarvelCharacter> {
        dataSource = if (query.isBlank()) CharacterDataSource(apiService, mapper, pagingPublisher)
        else CharacterDataSource(apiService, mapper, pagingPublisher, query)
        return dataSource
    }
}