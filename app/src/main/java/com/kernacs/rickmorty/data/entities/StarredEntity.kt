package com.kernacs.rickmorty.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class StarredEntity(
    @PrimaryKey
    val id: Int,
    val updatedDate : Long
)
