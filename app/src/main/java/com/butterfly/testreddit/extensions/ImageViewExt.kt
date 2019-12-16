package com.butterfly.testreddit.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Load image from uri to ImageView or set placeholder if load fails
 * load image as is without crop and rounding
 *
 * @param imageUri [String]
 *
 * @param placeholder [Int]
 */
fun ImageView.loadImageWithoutCrop(imageUri: String?, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().placeholder(placeholder).error(placeholder))
        .into(this)
}

/**
 * Load image from uri to ImageView or set placeholder if load fails
 * load image without crop and with rounding it
 *
 * @param imageUri [String]
 *
 * @param placeholder [Int]
 */
fun ImageView.loadCircularImage(imageUri: String?, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().placeholder(placeholder).error(placeholder).circleCrop())
        .into(this)
}

