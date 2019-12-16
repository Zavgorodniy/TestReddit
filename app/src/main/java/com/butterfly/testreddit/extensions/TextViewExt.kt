package com.butterfly.testreddit.extensions

import android.widget.TextView
import com.butterfly.testreddit.ui.EMPTY_STRING

/**
 * Hide [TextView] if @param[string] is empty
 *
 * @param string [String] text which must shown in TextView
 * @param isGone [Boolean] if true TextView will be gone, else invisible
 */
fun TextView.hideIfEmpty(string: String?, isGone: Boolean = true) =
    string.takeUnless { it.isNullOrBlank() }
        ?.let {
            show()
            text = it
        } ?: run {
        text = EMPTY_STRING
        hide(isGone)
    }