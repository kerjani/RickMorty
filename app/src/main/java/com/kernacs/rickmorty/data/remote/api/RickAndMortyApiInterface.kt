package com.kernacs.rickmorty.data.remote.api

import com.kernacs.rickmorty.data.dto.CharactersResponseDto
import com.kernacs.rickmorty.data.dto.EpisodeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiInterface {

    @GET(CHARACTERS)
    suspend fun getAllCharacters(
        @Query("page") page: Int? = null
    ): Response<CharactersResponseDto>

    @GET(CHARACTER)
    suspend fun getCharacterDetails(@Path("id") id: Int): Response<CharactersResponseDto.CharacterDto>

    @GET(EPISODE)
    suspend fun getEpisodeDetail(@Path("episodesOrId") id: Int): Response<EpisodeDto>

    @GET(EPISODE)
    suspend fun getEpisodes(@Path("episodesOrId") id: IntArray): Response<List<EpisodeDto>>

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val CHARACTERS = "character"
        const val CHARACTER = "character/{id}"
        const val EPISODE = "episode/{episodesOrId}"

    }


}