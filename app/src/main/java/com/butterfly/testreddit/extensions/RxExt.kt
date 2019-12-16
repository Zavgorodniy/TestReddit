package com.butterfly.testreddit.extensions

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.workAsync(): Single<T> =
    this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.doAsync(
    successful: MutableLiveData<T>,
    error: Consumer<Throwable>,
    loading: MediatorLiveData<Boolean>? = null,
    isShowProgress: Boolean = true
): Disposable =
    preSubscribe(loading, isShowProgress)
        .subscribe(Consumer { successful.value = it }, error)

private fun <T> Single<T>.preSubscribe(
    loading: MediatorLiveData<Boolean>?,
    isShowProgress: Boolean = true
): Single<T> {
    if (isShowProgress) loading?.postValue(isShowProgress)
    return workAsync().doOnEvent { _, _ -> if (isShowProgress) loading?.hideProgress() }
}

private fun MediatorLiveData<Boolean>.hideProgress() {
    postValue(false)
}