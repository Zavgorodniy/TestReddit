package com.butterfly.testreddit.models

import android.os.Parcelable

interface Model<T> : Parcelable {
    var id: T?
}