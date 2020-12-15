package br.com.mouzinho.domain.repository.character

import br.com.mouzinho.domain.entity.character.MarvelCharacter
import br.com.mouzinho.domain.entity.character.MarvelCharacterPagingResult
import io.reactivex.Observable
import io.reactivex.Single

interface MarvelCharacterRepository {

    fun loadCharactersPagedList(pageSize: Int): Observable<MarvelCharacterPagingResult>
    fun loadCharacterInfo(id: Int): Single<MarvelCharacter>
    fun sendSearchNameToPagingSource(name: String)
    fun reload()
}