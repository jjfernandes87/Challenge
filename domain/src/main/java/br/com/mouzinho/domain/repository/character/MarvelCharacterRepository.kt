package br.com.mouzinho.domain.repository.character

import br.com.mouzinho.domain.entity.character.MarvelCharacterLoadResult
import io.reactivex.Observable

interface MarvelCharacterRepository {

    fun loadCharactersPagedList(pageSize: Int): Observable<MarvelCharacterLoadResult>
    fun sendSearchNameToPagingSource(name: String)
    fun reload()
}