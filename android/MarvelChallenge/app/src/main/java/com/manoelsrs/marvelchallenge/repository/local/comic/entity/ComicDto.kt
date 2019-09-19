package com.manoelsrs.marvelchallenge.repository.local.comic.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ComicDto.TABLE)
data class ComicDto(
    @PrimaryKey val id: Int,
    val title: String,
    val photo: String,
    val photoExtension: String
) {
   companion object {
       const val TABLE = "comics_table"
   }
}