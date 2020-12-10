package br.com.mouzinho.domain.repository.character

import br.com.mouzinho.domain.entity.character.CharacterResult
import io.reactivex.Observable

interface CharacterRepository {

    fun getCharacters(limit: Int, offset: Int): Observable<CharacterResult>
}