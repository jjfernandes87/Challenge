package paixao.leonardo.marvel.heroes.data

import paixao.leonardo.marvel.heroes.data.models.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface MarvelGateway {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @QueryMap offSet: Map<String, Int>?
    ): CharactersResponse
}
