package paixao.leonardo.marvel.heroes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import paixao.leonardo.marvel.heroes.data.room.models.FavoriteCharacterCache

@Database(entities = [FavoriteCharacterCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): FavoritesCharacterCacheDao
}
