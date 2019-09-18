package com.manoelsrs.marvelchallenge.repository.local.character.entity

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface CharactersDao {

    @Query("SELECT * FROM ${CharactersDto.TABLE}")
    fun getCharacters(): DataSource.Factory<Int, CharactersDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: List<CharactersDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharactersDto)

    @Delete
    fun delete(character: CharactersDto)

    @Query("DELETE FROM ${CharactersDto.TABLE}")
    fun deleteAll()
}