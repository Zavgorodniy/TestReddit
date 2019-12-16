package com.butterfly.testreddit.extensions

import android.view.View
import android.view.View.*
import androidx.annotation.IdRes

/**
 * If gone set visibility GONE else INVISIBLE
 */
fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}

/**
 * Set view visibility VISIBLE
 */
fun View.show() {
    visibility = VISIBLE
}

/**
 * Find child view by id
 *
 * @param id [Int]
 *
 * @return [View]
 */
inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)

fun View?.setVisibility(isVisible: Boolean, gone: Boolean = true) =
    this?.let { if (isVisible) show() else hide(gone) }