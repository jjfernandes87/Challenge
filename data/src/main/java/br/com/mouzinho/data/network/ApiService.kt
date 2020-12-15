package br.com.mouzinho.data.network

import br.com.mouzinho.data.entity.ApiCharacterResponse
import br.com.mouzinho.data.entity.ApiComicResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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
        @Query("nameStartsWith") name: String
    ): Observable<ApiCharacterResponse>

    @GET
    fun getComicDetails(
        @Url url: String,
    ): Single<ApiComicResponse>
}