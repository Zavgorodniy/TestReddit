package com.butterfly.testreddit.extensions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.butterfly.testreddit.App

/**
 * Get string from resources
 *
 * @param resId [Int]
 *
 * @return [String]
 */
fun appString(@StringRes resId: Int) = App.instance.getString(resId)

fun appDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(App.instance, resId)
