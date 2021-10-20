package com.kernacs.rickmorty.data.local

import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.StarredEntity

interface LocalDataSource {
    suspend fun getCharacter(id: Int): CharacterEntity?
    suspend fun saveCharacters(items: List<CharacterEntity>)
    suspend fun deleteCharacters()

    suspend fun deleteEpisodes()
    suspend fun getEpisode(id: Int): EpisodeEntity?
    suspend fun getEpisodes(id: List<Int>): List<EpisodeEntity>
    suspend fun saveEpisodes(episodes: List<EpisodeEntity>)

    suspend fun deleteFavourites()
    suspend fun getFavourite(id: Int): StarredEntity?
    suspend fun getFavourites(): List<StarredEntity>
    suspend fun saveFavourite(fav: StarredEntity)
    suspend fun deleteFavourite(id: Int): Any
}