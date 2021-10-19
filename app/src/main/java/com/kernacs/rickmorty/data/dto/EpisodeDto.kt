package com.kernacs.rickmorty.data.dto


import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    @SerializedName("air_date")
    val airDate: String, // September 10, 2017
    val characters: List<String>,
    val created: String, // 2017-11-10T12:56:36.618Z
    val episode: String, // S03E07
    val id: Int, // 28
    val name: String, // The Ricklantis Mixup
    val url: String // https://rickandmortyapi.com/api/episode/28
)