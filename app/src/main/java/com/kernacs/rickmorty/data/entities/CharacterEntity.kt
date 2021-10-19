package com.kernacs.rickmorty.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "characters")
data class CharacterEntity(
    val created: Date, // 2017-11-04T18:48:46.250Z
    val episodeIds: List<Int>,
    val gender: String, // Male
    @PrimaryKey
    val id: Int, // 1
    val imageUrl: String, // https://rickandmortyapi.com/api/character/avatar/1.jpeg
    val location: String,
    val name: String, // Rick Sanchez
    val origin: String,
    val species: String, // Human
    val status: String, // Alive
    val type: String,
    val url: String // https://rickandmortyapi.com/api/character/1
)
