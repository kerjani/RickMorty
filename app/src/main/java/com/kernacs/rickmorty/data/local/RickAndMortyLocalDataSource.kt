package com.kernacs.rickmorty.data.local

import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.StarredEntity
import com.kernacs.rickmorty.data.local.dao.CharactersDao
import com.kernacs.rickmorty.data.local.dao.EpisodesDao
import com.kernacs.rickmorty.data.local.dao.FavouritesDao
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RickAndMortyLocalDataSource @Inject constructor(
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao,
    private val favouritesDao: FavouritesDao,
    private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {

    override suspend fun getCharacter(id: Int): CharacterEntity? = charactersDao.getCharacterBId(id)

    override suspend fun getEpisode(id: Int): EpisodeEntity? = episodesDao.getEpisodeBId(id)

    override suspend fun getEpisodes(id: List<Int>): List<EpisodeEntity> =
        episodesDao.getEpisodeBIds(id)

    override suspend fun saveCharacters(items: List<CharacterEntity>) =
        charactersDao.insertAll(items)

    override suspend fun saveEpisodes(episodes: List<EpisodeEntity>) =
        episodesDao.insertAll(episodes)

    override suspend fun deleteFavourites() = favouritesDao.deleteAll()

    override suspend fun getFavourite(id: Int): StarredEntity? = favouritesDao.getFavouriteBId(id)

    override suspend fun getFavourites(): List<StarredEntity> = favouritesDao.getFavourites()

    override suspend fun saveFavourite(fav: StarredEntity) = favouritesDao.insert(fav)

    override suspend fun deleteFavourite(id: Int) = favouritesDao.delete(id)

    override suspend fun deleteCharacters() = charactersDao.deleteAll()

    override suspend fun deleteEpisodes() = episodesDao.deleteAll()

}