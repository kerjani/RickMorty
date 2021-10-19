package com.kernacs.rickmorty.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodeEntity(
    val airDate: String, // September 10, 2017
    val characters: List<Int>,
    val created: String, // 2017-11-10T12:56:36.618Z
    val episode: String, // S03E07
    @PrimaryKey
    val id: Int, // 28
    val name: String, // The Ricklantis Mixup
    val url: String // https://rickandmortyapi.com/api/episode/28
)

fun EpisodeEntity.toTwoLineEpisode() =
    this.copy(episode = this.episode.replace("E", "\nE"))

