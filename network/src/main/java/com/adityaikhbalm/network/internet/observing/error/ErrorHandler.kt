package com.adityaikhbalm.network.internet.observing.error

interface ErrorHandler {
    fun handleError(exception: Exception?, message: String?)
}
