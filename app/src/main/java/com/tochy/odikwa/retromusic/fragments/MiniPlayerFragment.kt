package com.tochy.odikwa.retromusic.fragments

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.animation.DecelerateInterpolator
import com.tochy.odikwa.appthemehelper.ThemeStore
import com.tochy.odikwa.retromusic.R
import com.tochy.odikwa.retromusic.extensions.textColorPrimary
import com.tochy.odikwa.retromusic.extensions.textColorSecondary
import com.tochy.odikwa.retromusic.fragments.base.AbsMusicServiceFragment
import com.tochy.odikwa.retromusic.helper.MusicPlayerRemote
import com.tochy.odikwa.retromusic.helper.MusicProgressViewUpdateHelper
import com.tochy.odikwa.retromusic.helper.PlayPauseButtonOnClickHandler
import com.tochy.odikwa.retromusic.util.PreferenceUtil
import com.tochy.odikwa.retromusic.util.RetroUtil
import com.tochy.odikwa.retromusic.util.ViewUtil
import kotlinx.android.synthetic.main.fragment_mini_player.*
import kotlin.math.abs

open class MiniPlayerFragment : AbsMusicServiceFragment(), MusicProgressViewUpdateHelper.Callback,
    View.OnClickListener {

    private lateinit var progressViewUpdateHelper: MusicProgressViewUpdateHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressViewUpdateHelper = MusicProgressViewUpdateHelper(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mini_player, container, false)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.actionNext -> MusicPlayerRemote.playNextSong()
            R.id.actionPrevious -> MusicPlayerRemote.back()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnTouchListener(FlingPlayBackController(requireContext()))
        setUpMiniPlayer()

        if (RetroUtil.isTablet()) {
            actionNext.visibility = View.VISIBLE
            actionPrevious.visibility = View.VISIBLE
            actionNext?.visibility = View.VISIBLE
            actionPrevious?.visibility = View.VISIBLE
        } else {
            actionNext.visibility =
                if (PreferenceUtil.getInstance(requireContext()).isExtraControls) View.VISIBLE else View.GONE
            actionPrevious.visibility =
                if (PreferenceUtil.getInstance(requireContext()).isExtraControls) View.VISIBLE else View.GONE
        }
        actionNext.setOnClickListener(this)
        actionPrevious.setOnClickListener(this)
        actionNext?.setOnClickListener(this)
        actionPrevious?.setOnClickListener(this)
    }

    private fun setUpMiniPlayer() {
        setUpPlayPauseButton()
        ViewUtil.setProgressDrawable(progressBar, ThemeStore.accentColor(requireContext()))
    }

    private fun setUpPlayPauseButton() {
        miniPlayerPlayPauseButton.setOnClickListener(PlayPauseButtonOnClickHandler())
    }

    private fun updateSongTitle() {
        val builder = SpannableStringBuilder()

        val song = MusicPlayerRemote.currentSong
        val title = SpannableString(song.title)
        title.setSpan(ForegroundColorSpan(textColorPrimary(requireContext())), 0, title.length, 0)

        val text = SpannableString(song.artistName)
        text.setSpan(ForegroundColorSpan(textColorSecondary(requireContext())), 0, text.length, 0)

        builder.append(title).append(" • ").append(text)

        miniPlayerTitle.isSelected = true
        miniPlayerTitle.text = builder
    }

    override fun onServiceConnected() {
        updateSongTitle()
        updatePlayPauseDrawableState()
    }

    override fun onPlayingMetaChanged() {
        updateSongTitle()
    }

    override fun onPlayStateChanged() {
        updatePlayPauseDrawableState()
    }

    override fun onUpdateProgressViews(progress: Int, total: Int) {
        progressBar.max = total
        val animator = ObjectAnimator.ofInt(progressBar, "progress", progress)
        animator.duration = 1000
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    override fun onResume() {
        super.onResume()
        progressViewUpdateHelper.start()
    }

    override fun onPause() {
        super.onPause()
        progressViewUpdateHelper.stop()
    }

    protected fun updatePlayPauseDrawableState() {
        if (MusicPlayerRemote.isPlaying) {
            miniPlayerPlayPauseButton!!.setImageResource(R.drawable.ic_pause_white_24dp)
        } else {
            miniPlayerPlayPauseButton!!.setImageResource(R.drawable.ic_play_arrow_white_24dp)
        }
    }

    class FlingPlayBackController(context: Context) : View.OnTouchListener {

        private var flingPlayBackController: GestureDetector

        init {
            flingPlayBackController = GestureDetector(context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onFling(
                        e1: MotionEvent, e2: MotionEvent, velocityX: Float,
                        velocityY: Float
                    ): Boolean {
                        if (abs(velocityX) > abs(velocityY)) {
                            if (velocityX < 0) {
                                MusicPlayerRemote.playNextSong()
                                return true
                            } else if (velocityX > 0) {
                                MusicPlayerRemote.playPreviousSong()
                                return true
                            }
                        }
                        return false
                    }
                })
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return flingPlayBackController.onTouchEvent(event)
        }
    }
}
