package com.manoelsrs.marvelchallenge.repository.local.serie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SerieDto.TABLE)
data class SerieDto(
    @PrimaryKey val id: Int,
    val title: String,
    val photo: String,
    val photoExtension: String
) {
    companion object {
        const val TABLE = "series_table"
    }
}