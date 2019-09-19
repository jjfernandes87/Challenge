package com.manoelsrs.marvelchallenge.repository.local.serie.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SerieDao {

    @Query("SELECT COUNT(*) FROM ${SerieDto.TABLE}")
    fun getSeriesCount(): Single<Int>

    @Query("SELECT * FROM ${SerieDto.TABLE} ORDER BY title")
    fun getSeries(): Single<List<SerieDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(serieDto: List<SerieDto>)

    @Query("DELETE FROM ${SerieDto.TABLE}")
    fun deleteAll()
}