package com.manoelsrs.marvelchallenge.repository.local.favorite.entity

import androidx.room.*
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM ${FavoriteDto.TABLE} ORDER BY name")
    fun getFavorites(): Single<List<FavoriteDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteDto)

    @Delete
    fun delete(favorite: FavoriteDto)

    @Query("DELETE FROM ${FavoriteDto.TABLE}")
    fun deleteAll()
}