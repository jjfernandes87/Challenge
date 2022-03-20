package paixao.leonardo.marvel.heroes.lib

import paixao.leonardo.marvel.heroes.lib.models.CharactersResponse
import retrofit2.http.GET

interface MarvelGateway {

    @GET("v1/public/characters")
    suspend fun getCharacters(): CharactersResponse
}
