package com.manoelsrs.marvelchallenge.repository.remote.characters

import com.manoelsrs.marvelchallenge.repository.remote.characters.producers.MD5Hash
import com.manoelsrs.marvelchallenge.repository.remote.characters.resources.RemoteCharactersResources
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.services.CharactersServices
import io.reactivex.Single
import retrofit2.Response

class RemoteCharactersRepository(
    private val charactersServices: CharactersServices,
    private val md5Hash: MD5Hash
) : RemoteCharactersResources {

    companion object {
        private const val publicKey = "00ce064b34a7f59482129d4b3a33ee1c"
        private const val privateKey = "cd06400f1b3a06ad536b3df852e919ecc6bdeaba"
    }

    override fun getCharacters(limit: Int, offset: Int): Single<Response<CharactersResponse>> {
        val ts = System.currentTimeMillis().toString()
        return charactersServices.getCharacters(
            timestamp = ts,
            apikey = publicKey,
            hash = md5Hash.produce(ts, publicKey, privateKey),
            limit = limit,
            offset = offset
        )
    }
}