package com.manoelsrs.marvelchallenge.repository.remote.characters.resources

import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import io.reactivex.Single

interface RemoteCharactersResources {
    fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse>
}