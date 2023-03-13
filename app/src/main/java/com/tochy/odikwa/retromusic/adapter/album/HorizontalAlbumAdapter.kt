package com.tochy.odikwa.retromusic.adapter.album

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tochy.odikwa.appthemehelper.util.ATHUtil
import com.tochy.odikwa.retromusic.glide.AlbumGlideRequest
import com.tochy.odikwa.retromusic.glide.RetroMusicColoredTarget
import com.tochy.odikwa.retromusic.helper.HorizontalAdapterHelper
import com.tochy.odikwa.retromusic.interfaces.CabHolder
import com.tochy.odikwa.retromusic.model.Album
import com.tochy.odikwa.retromusic.util.MusicUtil
import com.bumptech.glide.Glide

class HorizontalAlbumAdapter(
    activity: AppCompatActivity,
    dataSet: List<Album>,
    cabHolder: CabHolder?
) : AlbumAdapter(
    activity, dataSet, HorizontalAdapterHelper.LAYOUT_RES, cabHolder
) {

    override fun createViewHolder(view: View, viewType: Int): ViewHolder {
        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        HorizontalAdapterHelper.applyMarginToLayoutParams(activity, params, viewType)
        return ViewHolder(view)
    }

    override fun setColors(color: Int, holder: ViewHolder) {
        holder.title?.setTextColor(ATHUtil.resolveColor(activity, android.R.attr.textColorPrimary))
        holder.text?.setTextColor(ATHUtil.resolveColor(activity, android.R.attr.textColorSecondary))
    }

    override fun loadAlbumCover(album: Album, holder: ViewHolder) {
        if (holder.image == null) return
        AlbumGlideRequest.Builder.from(Glide.with(activity), album.safeGetFirstSong())
            .checkIgnoreMediaStore(activity)
            .generatePalette(activity)
            .build()
            .into(object : RetroMusicColoredTarget(holder.image!!) {
                override fun onLoadCleared(placeholder: Drawable?) {
                    super.onLoadCleared(placeholder)
                    setColors(albumArtistFooterColor, holder)
                }

                override fun onColorReady(color: Int) {
                    setColors(color, holder)
                }
            })
    }

    override fun getAlbumText(album: Album): String? {
        return MusicUtil.getYearString(album.year)
    }

    override fun getItemViewType(position: Int): Int {
        return HorizontalAdapterHelper.getItemViewtype(position, itemCount)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    companion object {
        val TAG: String = AlbumAdapter::class.java.simpleName
    }
}
