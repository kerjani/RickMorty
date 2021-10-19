package com.kernacs.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kernacs.rickmorty.data.entities.CharacterEntity

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters ORDER BY status")
    suspend fun getCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id=:id")
    suspend fun getCharacterBId(id: Int): CharacterEntity?

    @Query("DELETE FROM characters")
    suspend fun deleteAll()
}