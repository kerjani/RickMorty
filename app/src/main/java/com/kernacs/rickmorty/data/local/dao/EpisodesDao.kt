package com.kernacs.rickmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kernacs.rickmorty.data.entities.EpisodeEntity

@Dao
interface EpisodesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE id=:id")
    suspend fun getEpisodeBId(id: Int): EpisodeEntity?

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Query("SELECT * FROM episodes WHERE id IN (:ids)")
    suspend fun getEpisodeBIds(ids: List<Int>): List<EpisodeEntity>
}