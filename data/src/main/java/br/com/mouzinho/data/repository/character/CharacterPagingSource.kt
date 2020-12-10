package br.com.mouzinho.data.repository.character

import androidx.paging.DataSource
import br.com.mouzinho.data.entity.character.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.mapper.Mapper
import javax.inject.Inject

class CharacterPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val mapper: Mapper<ApiMarvelCharacter, MarvelCharacter>
) : DataSource.Factory<Int, MarvelCharacter>() {
    var query = ""
    private lateinit var dataSource: DataSource<Int, MarvelCharacter>

    fun invalidate() = dataSource.invalidate()

    override fun create(): DataSource<Int, MarvelCharacter> {
        dataSource = if (query.isBlank()) CharacterDataSource(apiService, mapper)
        else CharacterDataSource(apiService, mapper, query)
        return dataSource
    }
}