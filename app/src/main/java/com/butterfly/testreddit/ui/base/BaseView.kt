package com.butterfly.testreddit.ui.base

interface BaseView {

    fun onError(error: Any)

    fun showProgress()

    fun hideProgress()
}