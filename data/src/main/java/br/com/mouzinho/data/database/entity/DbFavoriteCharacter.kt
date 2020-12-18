package br.com.mouzinho.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbFavoriteCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val landscapeThumbnailUrl: String?
)