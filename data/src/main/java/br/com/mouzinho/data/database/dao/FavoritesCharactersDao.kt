package br.com.mouzinho.data.database.dao

import androidx.room.*
import br.com.mouzinho.data.database.entity.DbFavoriteCharacter
import io.reactivex.Observable

@Dao
interface FavoritesCharactersDao {

    @Query("SELECT * FROM DbFavoriteCharacter")
    fun getAll(): Observable<List<DbFavoriteCharacter>>

    @Query("SELECT * FROM DbFavoriteCharacter WHERE name LIKE :name")
    fun getAllByName(name: String): Observable<List<DbFavoriteCharacter>>

    @Query("SELECT * FROM DbFavoriteCharacter WHERE id = :id")
    fun getById(id: Int): List<DbFavoriteCharacter>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(character: DbFavoriteCharacter): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: DbFavoriteCharacter): Long

    @Delete
    fun delete(character: DbFavoriteCharacter): Int
}