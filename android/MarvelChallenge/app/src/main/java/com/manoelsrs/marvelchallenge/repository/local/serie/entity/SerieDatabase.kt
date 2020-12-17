package com.manoelsrs.marvelchallenge.repository.local.serie.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(SerieDto::class)], version = 1)
abstract class SerieDatabase : RoomDatabase() {
    abstract fun serieDao(): SerieDao
}