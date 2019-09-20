package com.manoelsrs.marvelchallenge.repository.local.comic.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ComicDao {

    @Query("SELECT COUNT(*) FROM ${ComicDto.TABLE}")
    fun getComicsCount(): Single<Int>

    @Query("SELECT * FROM ${ComicDto.TABLE} ORDER BY title")
    fun getComics(): Single<List<ComicDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comicsDto: List<ComicDto>)

    @Query("DELETE FROM ${ComicDto.TABLE}")
    fun deleteAll()
}