package com.manoelsrs.marvelchallenge.repository.local.comic.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(ComicDto::class)], version = 1)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao
}