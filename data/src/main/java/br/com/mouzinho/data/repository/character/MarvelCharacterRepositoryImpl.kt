package br.com.mouzinho.data.repository.character

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import br.com.mouzinho.data.database.dao.FavoritesCharactersDao
import br.com.mouzinho.data.entity.ApiMarvelCharacter
import br.com.mouzinho.data.network.ApiService
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.character.MarvelCharacterPagingResult
import br.com.mouzinho.domain.mapper.Mapper
import br.com.mouzinho.domain.repository.character.MarvelCharacterRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MarvelCharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val characterMapper: Mapper<ApiMarvelCharacter, MarvelCharacter>,
    private val favoritesDao: FavoritesCharactersDao
) : MarvelCharacterRepository {
    private val pagingPublisher = PublishSubject.create<MarvelCharacterPagingResult>()
    private val pagingSource = CharacterDataSourceFactory(apiService, favoritesDao, characterMapper, pagingPublisher)

    override fun loadCharactersPagedList(pageSize: Int): Observable<MarvelCharacterPagingResult> {
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
                .map<MarvelCharacterPagingResult> {
                    MarvelCharacterPagingResult.Created(it)
                }
                .subscribe(pagingPublisher)
        }

    override fun sendSearchNameToPagingSource(name: String) {
        pagingSource.query = name
        pagingSource.invalidate()
    }

    override fun loadCharacterInfo(id: Int): Single<MarvelCharacter> {
        return apiService.getCharacterInfo(id)
            .map { response ->
                response.data?.results
                    ?.firstOrNull()
                    ?.let(characterMapper::transform)
                    ?.apply {
                        isFavorite = favoritesDao.getById(id).isNotEmpty()
                    }
                    ?: throw Exception("Character not found")
            }
    }

    override fun reload() {
        pagingSource.invalidate()
    }
}