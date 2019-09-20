package com.manoelsrs.marvelchallenge.repository.local.favorite.entity

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM ${FavoriteDto.TABLE} ORDER BY name")
    fun getFavorites(): DataSource.Factory<Int, FavoriteDto>

    @Query("SELECT * FROM ${FavoriteDto.TABLE} WHERE UPPER(name) LIKE '%' || :search || '%' ORDER BY name")
    fun getFavoritesSearch(search: String?): DataSource.Factory<Int, FavoriteDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoriteDto)

    @Delete
    fun delete(favorite: FavoriteDto)

    @Query("DELETE FROM ${FavoriteDto.TABLE}")
    fun deleteAll()
}