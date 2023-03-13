package com.tochy.odikwa.retromusic.fragments.base

import android.os.Bundle
import com.tochy.odikwa.retromusic.activities.MainActivity

open class AbsLibraryPagerFragment : AbsMusicServiceFragment() {

    val mainActivity: MainActivity
        get() = requireActivity() as MainActivity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }
}
