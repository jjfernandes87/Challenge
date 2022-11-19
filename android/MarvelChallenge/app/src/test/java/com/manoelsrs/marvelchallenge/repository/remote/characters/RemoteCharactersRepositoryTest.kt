package com.manoelsrs.marvelchallenge.repository.remote.characters

import com.manoelsrs.marvelchallenge.BuildConfig
import com.manoelsrs.marvelchallenge.repository.remote.Api
import com.manoelsrs.marvelchallenge.repository.remote.characters.producers.MD5Hash
import com.manoelsrs.marvelchallenge.repository.remote.characters.services.CharactersServices
import org.junit.Test
import retrofit2.HttpException

class RemoteCharactersRepositoryTest {

    private val remoteCharacters = RemoteCharactersRepository(
        Api<CharactersServices>().create(
            CharactersServices::class.java,
            BuildConfig.MARVEL_BASE_URL
        ),
        MD5Hash()
    )

    @Test
    fun `test remote characters success code`() {
        remoteCharacters.getCharacters(limit = 20, offset = 0)
            .test()
            .assertComplete()
    }

    @Test
    fun `test remote characters size response`() {
        remoteCharacters.getCharacters(limit = 20, offset = 0)
            .test()
            .assertValue { it.data.results.size == 20 }
    }

    @Test
    fun `test remote characters offset`() {
        val offset = 10
        remoteCharacters.getCharacters(limit = 20, offset = offset)
            .test()
            .assertValue { it.data.offset == offset }
    }

    @Test
    fun `You may not request more than 100 items`() {
        val offset = 20
        remoteCharacters.getCharacters(limit = 101, offset = offset)
            .test()
            .assertError{ it is HttpException }
    }
}