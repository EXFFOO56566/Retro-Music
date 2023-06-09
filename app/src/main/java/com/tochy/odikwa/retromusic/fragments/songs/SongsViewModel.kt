package com.tochy.odikwa.retromusic.fragments.songs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tochy.odikwa.retromusic.Result.Success
import com.tochy.odikwa.retromusic.model.Song
import com.tochy.odikwa.retromusic.providers.RepositoryImpl
import kotlinx.coroutines.launch

class SongsViewModel(application: Application) : AndroidViewModel(application) {
    var songs = MutableLiveData<List<Song>>()

    init {
        loadSongs()
    }

    fun loadSongs() = viewModelScope.launch {
        val result = RepositoryImpl(getApplication()).allSongs()
        if (result is Success) {
            songs.value = result.data
        } else {
            songs.value = listOf()
        }
    }
}