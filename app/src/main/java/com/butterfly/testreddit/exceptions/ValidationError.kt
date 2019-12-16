package com.butterfly.testreddit.exceptions

data class ValidationError(
    var code: Int?,
    var key: String?,
    var message: String?
)
