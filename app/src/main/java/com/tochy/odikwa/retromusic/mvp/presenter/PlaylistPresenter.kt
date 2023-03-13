/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.tochy.odikwa.retromusic.mvp.presenter

import com.tochy.odikwa.retromusic.Result
import com.tochy.odikwa.retromusic.model.Playlist
import com.tochy.odikwa.retromusic.mvp.BaseView
import com.tochy.odikwa.retromusic.mvp.Presenter
import com.tochy.odikwa.retromusic.mvp.PresenterImpl
import com.tochy.odikwa.retromusic.providers.interfaces.Repository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by hemanths on 19/08/17.
 */

interface PlaylistView : BaseView {

    fun playlists(playlists: List<Playlist>)
}

interface PlaylistsPresenter : Presenter<PlaylistView> {

    fun playlists()

    class PlaylistsPresenterImpl @Inject constructor(
        private val repository: Repository
    ) : PresenterImpl<PlaylistView>(), PlaylistsPresenter, CoroutineScope {

        private val job = Job()

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.IO + job

        override fun detachView() {
            super.detachView()
            job.cancel()
        }

        override fun playlists() {
            launch {
                when (val result = repository.allPlaylists()) {
                    is Result.Success -> withContext(Dispatchers.Main) { view?.playlists(result.data) }
                    is Result.Error -> withContext(Dispatchers.Main) { view?.showEmptyView() }
                }
            }
        }
    }
}


