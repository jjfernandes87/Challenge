package com.manoelsrs.marvelchallenge.repository.remote.characters

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
        private const val publicKey = "00ce064b34a7f59482129d4b3a33ee1c"
        private const val privateKey = "cd06400f1b3a06ad536b3df852e919ecc6bdeaba"
        private const val COMICS = "comics"
        private const val SERIES = "series"
    }

    override fun getCharacters(limit: Int, offset: Int): Single<CharactersResponse> {
        val ts = System.currentTimeMillis().toString()
        return charactersServices.getCharacters(
            timestamp = ts,
            apikey = publicKey,
            hash = md5Hash.produce(ts, publicKey, privateKey),
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
            apikey = publicKey,
            hash = md5Hash.produce(ts, publicKey, privateKey),
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
            apikey = publicKey,
            hash = md5Hash.produce(ts, publicKey, privateKey),
            characterId = characterId, limit = limit, offset = offset,
            type = type
        )
    }
}