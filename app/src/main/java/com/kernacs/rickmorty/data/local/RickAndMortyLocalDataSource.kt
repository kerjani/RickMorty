package com.kernacs.rickmorty.data.local

import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.StarredEntity
import com.kernacs.rickmorty.data.local.dao.CharactersDao
import com.kernacs.rickmorty.data.local.dao.EpisodesDao
import com.kernacs.rickmorty.data.local.dao.FavouritesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyLocalDataSource @Inject constructor(
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao,
    private val favouritesDao: FavouritesDao,
    private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {
    override suspend fun getCharacters(): List<CharacterEntity> = withContext(ioDispatcher) {
        return@withContext charactersDao.getCharacters()
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? = withContext(ioDispatcher) {
        return@withContext charactersDao.getCharacterBId(id)
    }

    override suspend fun getEpisode(id: Int): EpisodeEntity? = withContext(ioDispatcher) {
        return@withContext episodesDao.getEpisodeBId(id)
    }

    override suspend fun getEpisodes(id: List<Int>): List<EpisodeEntity> =
        withContext(ioDispatcher) {
            return@withContext episodesDao.getEpisodeBIds(id)
        }

    override suspend fun saveCharacters(items: List<CharacterEntity>) = withContext(ioDispatcher) {
        return@withContext charactersDao.insertAll(items)
    }

    override suspend fun saveEpisodes(episodes: List<EpisodeEntity>) = withContext(ioDispatcher) {
        return@withContext episodesDao.insertAll(episodes)
    }

    override suspend fun deleteFavourites() = withContext(ioDispatcher) {
        return@withContext favouritesDao.deleteAll()
    }

    override suspend fun getFavourite(id: Int): StarredEntity? = withContext(ioDispatcher) {
        return@withContext favouritesDao.getFavouriteBId(id)
    }

    override suspend fun getFavourites(): List<StarredEntity> = withContext(ioDispatcher) {
        return@withContext favouritesDao.getFavourites()
    }

    override suspend fun saveFavourite(fav: StarredEntity) = withContext(ioDispatcher) {
        return@withContext favouritesDao.insert(fav)
    }

    override suspend fun deleteFavourite(id: Int) = withContext(ioDispatcher) {
        return@withContext favouritesDao.delete(id)
    }


    override suspend fun deleteCharacters() = withContext(ioDispatcher) {
        return@withContext charactersDao.deleteAll()
    }

    override suspend fun deleteEpisodes() = withContext(ioDispatcher) {
        return@withContext episodesDao.deleteAll()
    }


}