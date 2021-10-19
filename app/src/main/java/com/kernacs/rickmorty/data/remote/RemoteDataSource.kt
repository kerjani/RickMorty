package com.kernacs.rickmorty.data.remote

import com.kernacs.rickmorty.data.dto.CharactersResponseDto
import com.kernacs.rickmorty.data.dto.EpisodeDto
import com.kernacs.rickmorty.util.Result

abstract class RemoteDataSource /*: PagingSource<Int, CharactersResponseDto.CharacterDto>()*/ {
    abstract suspend fun getCharacters(page: Int?): Result<List<CharactersResponseDto.CharacterDto>>
    abstract suspend fun getCharacter(id: Int): Result<CharactersResponseDto.CharacterDto>
    abstract suspend fun getEpisodes(ids: IntArray): Result<List<EpisodeDto>>
    abstract suspend fun getEpisode(id: Int): Result<EpisodeDto>
    //suspend fun getImageInfo(id: Int): Result<ImageInfo>
}