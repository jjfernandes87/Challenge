package br.com.mouzinho.domain.repository.character

import androidx.paging.PagedList
import br.com.mouzinho.domain.entity.character.MarvelCharacter
import io.reactivex.Observable

interface CharacterRepository {

    fun loadCharactersPagedList(pageSize: Int): Observable<PagedList<MarvelCharacter>>
    fun sendSearchNameToPagingSource(name: String)
}