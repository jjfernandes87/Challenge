package br.com.mouzinho.data.network

import br.com.mouzinho.data.entity.character.ApiCharacterResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/public/characters")
    fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<ApiCharacterResponse>

    @GET("v1/public/characters")
    fun searchCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("name") name: String
    ): Observable<ApiCharacterResponse>
}