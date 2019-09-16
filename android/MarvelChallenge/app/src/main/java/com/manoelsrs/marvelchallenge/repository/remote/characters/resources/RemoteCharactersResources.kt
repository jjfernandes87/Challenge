package com.manoelsrs.marvelchallenge.repository.remote.characters.resources

import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import io.reactivex.Single
import retrofit2.Response

interface RemoteCharactersResources {
    fun getCharacters(limit: Int, offset: Int): Single<Response<CharactersResponse>>
}