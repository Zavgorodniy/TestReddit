package com.butterfly.testreddit.ui.base.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<in T>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T, position: Int)
}