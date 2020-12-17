package com.manoelsrs.marvelchallenge.repository.local.favorite.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(FavoriteDto::class)], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}