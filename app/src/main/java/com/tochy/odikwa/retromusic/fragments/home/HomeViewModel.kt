package com.tochy.odikwa.retromusic.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tochy.odikwa.retromusic.Result
import com.tochy.odikwa.retromusic.model.Home
import com.tochy.odikwa.retromusic.providers.RepositoryImpl
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var sections = MutableLiveData<List<Home>>()
    var repository: RepositoryImpl = RepositoryImpl(getApplication())

    init {
        loadHome()
    }

    fun loadHome() = viewModelScope.launch {
        val list = mutableListOf<Home>()
        val result = listOf(
            repository.topArtists(),
            repository.topAlbums(),
            repository.recentArtists(),
            repository.recentAlbums(),
            repository.favoritePlaylist()
        )
        for (r in result) {
            when (r) {
                is Result.Success -> list.add(r.data)
            }
        }
        sections.value = list
    }
}