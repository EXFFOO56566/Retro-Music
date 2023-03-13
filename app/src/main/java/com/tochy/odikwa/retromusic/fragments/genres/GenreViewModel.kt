package com.tochy.odikwa.retromusic.fragments.genres

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tochy.odikwa.retromusic.Result.Success
import com.tochy.odikwa.retromusic.model.Genre
import com.tochy.odikwa.retromusic.providers.RepositoryImpl
import kotlinx.coroutines.launch

class GenreViewModel(application: Application) : AndroidViewModel(application) {
    var genres = MutableLiveData<List<Genre>>()

    init {
        loadGenre()
    }

    fun loadGenre() = viewModelScope.launch {
        val result = RepositoryImpl(getApplication()).allGenres()
        if (result is Success) {
            genres.value = result.data
        }else {
            genres.value = listOf()
        }
    }
}