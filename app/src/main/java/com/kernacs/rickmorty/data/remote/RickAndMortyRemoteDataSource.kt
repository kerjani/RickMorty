package com.kernacs.rickmorty.data.remote

import com.kernacs.rickmorty.data.dto.CharactersResponseDto
import com.kernacs.rickmorty.data.dto.EpisodeDto
import com.kernacs.rickmorty.data.remote.api.RickAndMortyApiInterface
import com.kernacs.rickmorty.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyRemoteDataSource @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: RickAndMortyApiInterface
) : RemoteDataSource() {

    override suspend fun getCharacters(page: Int?): Result<List<CharactersResponseDto.CharacterDto>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = api.getAllCharacters(page)
                if (result.isSuccessful) {
                    result.body()?.let {
                        Result.Success(it.results)
                    }
                        ?: Result.Error(Exception("Download of characters at page $page has been failed"))
                } else {
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

    override suspend fun getCharacter(id: Int): Result<CharactersResponseDto.CharacterDto> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = api.getCharacterDetails(id)
                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

    override suspend fun getEpisodes(ids: IntArray): Result<List<EpisodeDto>> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = api.getEpisodes(ids)
                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }
        }

    override suspend fun getEpisode(id: Int): Result<EpisodeDto> =
        withContext(ioDispatcher) {
            return@withContext try {
                val result = api.getEpisodeDetail(id)
                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (exception: Exception) {
                Result.Error(exception)
            }

        }

/*

    override fun getRefreshKey(state: PagingState<Int, CharactersResponseDto.CharacterDto>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponseDto.CharacterDto> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response: Response<CharactersResponseDto> = api.getAllCharacters(position)

            when {
                response.isSuccessful -> {
                    val result = response.body()?.results ?: emptyList()
                    val nextKey = if (result.isNullOrEmpty()) {
                        null
                    } else {
                        // initial load size = 3 * NETWORK_PAGE_SIZE
                        // ensure we're not requesting duplicating items, at the 2nd request
                        position + (params.loadSize / PAGE_SIZE)
                    }
                    LoadResult.Page(
                        data = result,
                        prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                        nextKey = nextKey
                    )
                }
                else -> {
                    LoadResult.Error(Exception(response.errorBody().toString()))
                }
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val PAGE_SIZE = 20
    }*/
}