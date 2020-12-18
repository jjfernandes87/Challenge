package br.com.mouzinho.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mouzinho.data.database.converter.StringListConverter
import br.com.mouzinho.data.database.dao.FavoritesCharactersDao
import br.com.mouzinho.data.database.entity.DbFavoriteCharacter

@Database(
    entities = [DbFavoriteCharacter::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritesCharactersDao(): FavoritesCharactersDao

    companion object {
        private const val DATABASE_NAME = "marvel-database"

        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}