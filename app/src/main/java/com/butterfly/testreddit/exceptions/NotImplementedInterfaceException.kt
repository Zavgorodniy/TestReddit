package com.butterfly.testreddit.exceptions

class NotImplementedInterfaceException(clazz: Class<*>) :
    ClassCastException(String.format(MESSAGE, clazz)) {

    companion object {
        private const val MESSAGE = "You need to implement %s"
    }
}