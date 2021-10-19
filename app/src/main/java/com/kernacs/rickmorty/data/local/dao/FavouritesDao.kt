package com.kernacs.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.StarredEntity

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(starredEntity: StarredEntity)

    @Query("SELECT * FROM favourites ORDER BY updatedDate DESC")
    suspend fun getFavourites(): List<StarredEntity>

    @Query("SELECT * FROM favourites WHERE id=:id")
    suspend fun getFavouriteBId(id: Int): StarredEntity?

    @Query("DELETE FROM favourites")
    suspend fun deleteAll()

    @Query("DELETE FROM favourites WHERE id=:id")
    fun delete(id: Int)
}