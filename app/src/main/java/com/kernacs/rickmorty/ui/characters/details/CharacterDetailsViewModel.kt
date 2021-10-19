package com.kernacs.rickmorty.ui.characters.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.entities.StarredEntity
import com.kernacs.rickmorty.data.local.RickAndMortyLocalDataSource
import com.kernacs.rickmorty.data.repository.Repository
import com.kernacs.rickmorty.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: Repository,
    private val localDataSource: RickAndMortyLocalDataSource,
) : ViewModel() {


    val isFavourite = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    val characterData = MutableLiveData<CharacterEntity>()
    val episodes = MutableLiveData<List<EpisodeEntity>>()

    fun getCharacterData(id: Int) {
        Log.d(TAG, "Attempt to refresh character detail data")
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getCharacter(id)) {
                is Result.Success -> {
                    isLoading.value = false
                    characterData.value = result.data!!
                    getEpisodes(result.data.episodeIds)
                    Log.d(TAG, "Refresh of the character data is successful: ${result.data}")
                }
                is Result.Error -> {
                    Log.d(TAG, "Error during data refresh: ${result.exception}")
                    isLoading.value = false
                    error.value = result.exception.message
                }
                is Result.Loading -> isLoading.postValue(true)
            }
            isFavourite.value = localDataSource.getFavourite(id)!=null
        }
    }

    private fun getEpisodes(ids: List<Int>) {
        Log.d(TAG, "download episodes with ids: $ids")
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getEpisodes(ids)) {
                is Result.Success -> {
                    isLoading.value = true
                    episodes.value = result.data!!
                }
                is Result.Error -> {
                    isLoading.value = false
                    error.value = result.exception.message
                }
                Result.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }

    fun saveFavourite(id: Int, isFavourite: Boolean) = viewModelScope.launch {
        if(isFavourite) {
            localDataSource.saveFavourite(StarredEntity(id, Date().time))
        } else {
            localDataSource.deleteFavourite(id)
        }
    }

    companion object {
        var TAG = CharacterDetailsViewModel::class.java.simpleName
    }
}