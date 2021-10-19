package com.kernacs.rickmorty.ui.episodes.details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.entities.EpisodeEntity
import com.kernacs.rickmorty.data.repository.Repository
import com.kernacs.rickmorty.ui.characters.details.CharacterDetailsViewModel
import com.kernacs.rickmorty.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    val episodeData = MutableLiveData<EpisodeEntity>()
    val characters = MutableLiveData<List<CharacterEntity>>()

    fun getCharacters(ids: List<Int>) {
        Log.d(TAG, "Attempt to refresh character detail data")
        isLoading.value = true
        viewModelScope.launch {

            characters.value = ids.mapNotNull {
                when (val result = repository.getCharacter(it)) {
                    is Result.Success -> {
                        isLoading.value = false
                        Log.d(TAG, "Refresh of the character data is successful: ${result.data}")
                        result.data!!
                    }
                    is Result.Error -> {
                        Log.d(TAG, "Error during data refresh: ${result.exception}")
                        isLoading.value = false
                        error.value = result.exception.message
                        null
                    }
                    is Result.Loading -> {
                        isLoading.postValue(true)
                        null
                    }
                }
            }
        }
    }

    fun getEpisode(id: Int) {
        Log.d(TAG, "get episode data with id: $id")
        isLoading.value = true
        viewModelScope.launch {
            when (val result = repository.getEpisode(id)) {
                is Result.Success -> {
                    isLoading.value = true
                    val data = result.data
                    data?.let { episodeData.value = it }
                    getCharacters(data?.characters ?: emptyList())
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

    companion object {
        var TAG = CharacterDetailsViewModel::class.java.simpleName
    }
}