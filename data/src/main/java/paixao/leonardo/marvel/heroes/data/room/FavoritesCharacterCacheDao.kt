package paixao.leonardo.marvel.heroes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import paixao.leonardo.marvel.heroes.data.room.models.FavoriteCharacterCache

@Dao
interface FavoritesCharacterCacheDao {

    @Query("SELECT * FROM favorites ORDER BY name")
    fun getAll(): List<FavoriteCharacterCache>

    @Insert
    fun insert(character: FavoriteCharacterCache)

    @Delete
    fun delete(character: FavoriteCharacterCache)
}
