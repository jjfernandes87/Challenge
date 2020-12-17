package com.manoelsrs.marvelchallenge.repository.local.character.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(CharactersDto::class)], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}