package com.adityaikhbalm.network.internet.observing.error

import android.util.Log
import com.adityaikhbalm.network.ReactiveNetwork

class DefaultErrorHandler :
    ErrorHandler {
    override fun handleError(
        exception: Exception?,
        message: String?
    ) {
        Log.e(ReactiveNetwork.LOG_TAG, message, exception)
    }
}
