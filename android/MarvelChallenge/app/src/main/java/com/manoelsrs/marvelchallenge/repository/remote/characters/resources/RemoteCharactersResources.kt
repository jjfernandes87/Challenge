package com.manoelsrs.marvelchallenge.repository.remote.characters.resources

import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import io.reactivex.Single

interface RemoteCharactersResources {
    fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse>
    fun getCharacters(
        limit: Int,
        offset: Int,
        nameStartsWith: String = ""
    ): Single<CharactersResponse>
    fun getComics(characterId: Int, limit: Int, offset: Int): Single<CSResponse>
    fun getSeries(characterId: Int, limit: Int, offset: Int): Single<CSResponse>
}