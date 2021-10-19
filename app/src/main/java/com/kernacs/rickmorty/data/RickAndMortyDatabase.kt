package com.kernacs.rickmorty.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.StarredEntity
import com.kernacs.rickmorty.data.local.dao.CharactersDao
import com.kernacs.rickmorty.data.local.dao.EpisodesDao
import com.kernacs.rickmorty.data.local.dao.FavouritesDao
import com.kernacs.rickmorty.util.TypeConverters

@Database(
    entities = [CharacterEntity::class, EpisodeEntity::class, StarredEntity::class],
    version = 1,
    exportSchema = false
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun episodesDao(): EpisodesDao
    abstract fun favouritesDao(): FavouritesDao

    companion object {

        @Volatile
        private var instance: RickAndMortyDatabase? = null

        fun getDatabase(context: Context): RickAndMortyDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                RickAndMortyDatabase::class.java,
                "rickAndMortyDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}