package com.manoelsrs.marvelchallenge.repository.local.favorite.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavoriteDto.TABLE)
data class FavoriteDto(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val photo: String,
    val photoExtension: String,
    val hasComics: Boolean,
    val hasSeries: Boolean
) {
    companion object {
        const val TABLE = "favorite_table"
    }
}