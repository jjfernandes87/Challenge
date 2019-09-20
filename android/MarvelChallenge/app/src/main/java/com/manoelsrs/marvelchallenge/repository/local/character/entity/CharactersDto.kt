package com.manoelsrs.marvelchallenge.repository.local.character.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CharactersDto.TABLE)
data class CharactersDto(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val photo: String,
    val photoExtension: String,
    val hasComics: Boolean,
    val hasSeries: Boolean
) {
    companion object {
        const val TABLE = "characters_table"
    }
}