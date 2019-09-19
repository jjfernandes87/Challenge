package com.manoelsrs.marvelchallenge.repository.remote.characters.services

import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CSResponse
import com.manoelsrs.marvelchallenge.repository.remote.characters.responses.CharactersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersServices {

    @GET("v1/public/characters")
    fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("orderBy") orderBy: String = "name",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<CharactersResponse>

    @GET("v1/public/characters")
    fun getCharacters(
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("orderBy") orderBy: String = "name",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<CharactersResponse>

    @GET("/v1/public/characters/{characterId}/comics")
    fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") timestamp: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("orderBy") orderBy: String = "title",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<CSResponse>
}