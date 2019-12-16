package com.butterfly.testreddit.exceptions

import com.butterfly.testreddit.R
import com.butterfly.testreddit.extensions.appString

class NoNetworkException : Exception() {

    companion object {
        private val ERROR_MESSAGE = appString(R.string.no_internet_connection_error)
    }

    override val message: String = ERROR_MESSAGE
}
