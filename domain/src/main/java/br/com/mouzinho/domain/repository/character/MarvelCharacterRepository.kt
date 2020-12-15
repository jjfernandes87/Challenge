package br.com.mouzinho.domain.repository.character

import br.com.mouzinho.domain.entity.character.MarvelCharacterPagingResult
import io.reactivex.Observable

interface MarvelCharacterRepository {

    fun loadCharactersPagedList(pageSize: Int): Observable<MarvelCharacterPagingResult>
    fun sendSearchNameToPagingSource(name: String)
    fun reload()
}