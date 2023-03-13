package com.tochy.odikwa.appthemehelper.common

import androidx.appcompat.widget.Toolbar

import com.tochy.odikwa.appthemehelper.util.ToolbarContentTintHelper

class ATHActionBarActivity : ATHToolbarActivity() {

    override fun getATHToolbar(): Toolbar? {
        return ToolbarContentTintHelper.getSupportActionBarView(supportActionBar)
    }
}
