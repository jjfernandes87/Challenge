package paixao.leonardo.marvel.heroes.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCharacterCache(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String? = null,
    val imageUrl: String
)
