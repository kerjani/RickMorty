package com.kernacs.rickmorty.ui.favourites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kernacs.rickmorty.data.entities.CharacterEntity
import com.kernacs.rickmorty.data.local.LocalDataSource
import com.kernacs.rickmorty.data.repository.Repository
import com.kernacs.rickmorty.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteCharactersViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val repository: Repository,
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()
    val characters = MutableLiveData<List<CharacterEntity>>()

    fun getCharacters() {
        Log.d(TAG, "Attempt to refresh character detail data")
        isLoading.value = true
        viewModelScope.launch {

            characters.value = localDataSource.getFavourites().mapNotNull {
                when (val result = repository.getCharacter(it.id)) {
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

    companion object {
        const val TAG = "FavouriteCharactersViewModel"
    }
}