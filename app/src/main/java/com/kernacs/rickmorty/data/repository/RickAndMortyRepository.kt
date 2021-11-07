package com.kernacs.rickmorty.data.repository

import com.kernacs.rickmorty.data.dto.mapping.toEntity
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.local.LocalDataSource
import com.kernacs.rickmorty.data.remote.RemoteDataSource
import com.kernacs.rickmorty.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override suspend fun getCharacters(page: Int?): Result<List<CharacterEntity>> =
        withContext(ioDispatcher) {
            when (val response = remoteDataSource.getCharacters(page)) {
                is Result.Success -> {
                    if (response.data == null) {
                        Result.Error(IllegalArgumentException("Page $page has no data"))
                        //remoteDataSource.getCharacters(1)
                    } else {
                        response.data.map { it.toEntity() }.let {
                            localDataSource.saveCharacters(it)
                            Result.Success(it)
                        }
                    }
                }

                is Result.Error -> {
                    Result.Error(response.exception)
                }

                else -> Result.Loading
            }
        }

    override suspend fun getCharacter(id: Int): Result<CharacterEntity> =
        withContext(ioDispatcher) {
            localDataSource.getCharacter(id)?.let {
                Result.Success(it)
            } ?: when (val downloadedResult = remoteDataSource.getCharacter(id)) {
                is Result.Success -> {
                    downloadedResult.data?.let {
                        val entity = it.toEntity()
                        localDataSource.saveCharacters(listOf(entity))
                        Result.Success(entity)
                    }
                        ?: Result.Error(Exception("Download of character with id $id has been failed"))
                }
                is Result.Error -> {
                    Result.Error(downloadedResult.exception)
                }
                Result.Loading -> {
                    Result.Loading
                }
            }
        }

    override suspend fun saveCharacters(items: List<CharacterEntity>) =
        withContext(ioDispatcher) {
            localDataSource.saveCharacters(items)
        }

    override suspend fun deleteCharacters() = localDataSource.deleteCharacters()


    override suspend fun deleteEpisodes() = localDataSource.deleteEpisodes()


    override suspend fun saveEpisodes(items: List<EpisodeEntity>) =
        localDataSource.saveEpisodes(items)


    override suspend fun getEpisodes(ids: List<Int>): Result<List<EpisodeEntity>> =
        ids.mapNotNull {
            localDataSource.getEpisode(it) ?: run {
                when (val result = getEpisode(it)) {
                    is Result.Success -> {
                        result.data
                    }
                    else -> null
                }
            }
        }.let {
            Result.Success(it)
        }


    override suspend fun getEpisode(id: Int): Result<EpisodeEntity> =
        localDataSource.getEpisode(id)?.let {
            Result.Success(it)
        } ?: when (val downloadedResult = remoteDataSource.getEpisode(id)) {
            is Result.Success -> {
                downloadedResult.data?.let {
                    val entity = it.toEntity()
                    localDataSource.saveEpisodes(listOf(entity))
                    Result.Success(entity)
                }
                    ?: Result.Error(Exception("Download of character with id $id has been failed"))
            }
            is Result.Error -> {
                Result.Error(downloadedResult.exception)
            }
            Result.Loading -> {
                Result.Loading
            }
        }
}