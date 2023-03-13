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

package com.tochy.odikwa.retromusic.dagger

import com.tochy.odikwa.retromusic.activities.*
import com.tochy.odikwa.retromusic.dagger.module.AppModule
import com.tochy.odikwa.retromusic.dagger.module.PresenterModule
import com.tochy.odikwa.retromusic.fragments.albums.AlbumsFragment
import com.tochy.odikwa.retromusic.fragments.artists.ArtistsFragment
import com.tochy.odikwa.retromusic.fragments.genres.GenresFragment
import com.tochy.odikwa.retromusic.fragments.home.BannerHomeFragment
import com.tochy.odikwa.retromusic.fragments.playlists.PlaylistsFragment
import com.tochy.odikwa.retromusic.fragments.songs.SongsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by hemanths on 2019-09-04.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        PresenterModule::class
    ]
)
interface MusicComponent {

    fun inject(songsFragment: SongsFragment)

    fun inject(albumsFragment: AlbumsFragment)

    fun inject(artistsFragment: ArtistsFragment)

    fun inject(genresFragment: GenresFragment)

    fun inject(playlistsFragment: PlaylistsFragment)

    fun inject(artistDetailActivity: ArtistDetailActivity)

    fun inject(albumDetailsActivity: AlbumDetailsActivity)

    fun inject(playlistDetailActivity: PlaylistDetailActivity)

    fun inject(genreDetailsActivity: GenreDetailsActivity)

    fun inject(searchActivity: SearchActivity)

    fun inject(bannerHomeFragment: BannerHomeFragment)
}
