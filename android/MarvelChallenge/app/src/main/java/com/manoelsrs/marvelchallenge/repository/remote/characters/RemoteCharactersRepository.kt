package com.manoelsrs.marvelchallenge.repository.remote.characters

import com.manoelsrs.marvelchallenge.BuildConfig
import com.manoelsrs.marvelchallenge.repository.remote.characters.producers.MD5Hash
import com.manoelsrs.marvelchallenge.repository.remote.characters.resources.RemoteCharactersResources
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.services.CharactersServices
import io.reactivex.Single

class RemoteCharactersRepository(
    private val charactersServices: CharactersServices,
    private val md5Hash: MD5Hash
) : RemoteCharactersResources {

    companion object {
        private const val COMICS = "comics"
        private const val SERIES = "series"
    }

    override fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse> {
        val ts = System.currentTimeMillis().toString()
        return charactersServices.getCharacters(
            timestamp = ts,
            apikey = BuildConfig.MARVEL_PUBLIC_KEY,
            hash = md5Hash.produce(
                ts,
                BuildConfig.MARVEL_PUBLIC_KEY,
                BuildConfig.MARVEL_PRIVATE_KEY
            ),
            limit = limit,
            offset = offset
        )
    }

    override fun getCharacters(
        limit: Int,
        offset: Int,
        nameStartsWith: String
    ): Single<CharactersResponse> {
        val ts = System.currentTimeMillis().toString()
        return charactersServices.getCharacters(
            timestamp = ts,
            apikey = BuildConfig.MARVEL_PUBLIC_KEY,
            hash = md5Hash.produce(
                ts,
                BuildConfig.MARVEL_PUBLIC_KEY,
                BuildConfig.MARVEL_PRIVATE_KEY
            ),
            nameStartsWith = nameStartsWith,
            limit = limit,
            offset = offset
        )
    }

    override fun getComics(characterId: Int, limit: Int, offset: Int): Single<CSResponse> {
        return getData(characterId, limit, offset, COMICS)
    }

    override fun getSeries(characterId: Int, limit: Int, offset: Int): Single<CSResponse> {
        return getData(characterId, limit, offset, SERIES)
    }

    private fun getData(
        characterId: Int,
        limit: Int,
        offset: Int,
        type: String
    ): Single<CSResponse> {
        val ts = System.currentTimeMillis().toString()
        return charactersServices.getData(
            timestamp = ts,
            apikey = BuildConfig.MARVEL_PUBLIC_KEY,
            hash = md5Hash.produce(
                ts,
                BuildConfig.MARVEL_PUBLIC_KEY,
                BuildConfig.MARVEL_PRIVATE_KEY
            ),
            characterId = characterId, limit = limit, offset = offset,
            type = type
        )
    }
}