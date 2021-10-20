package com.kernacs.rickmorty.data.repository

import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity

import com.kernacs.rickmorty.util.Result

interface Repository {
    suspend fun getCharacters(page: Int?): Result<List<CharacterEntity>>
    suspend fun getCharacter(id: Int): Result<CharacterEntity>
    suspend fun saveCharacters(items: List<CharacterEntity>)
    suspend fun deleteCharacters()
    suspend fun deleteEpisodes()
    suspend fun saveEpisodes(items: List<EpisodeEntity>)
    suspend fun getEpisodes(ids: List<Int>): Result<List<EpisodeEntity>>
    suspend fun getEpisode(id: Int): Result<EpisodeEntity>
}