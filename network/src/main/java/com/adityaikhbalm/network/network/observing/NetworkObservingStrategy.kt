package com.adityaikhbalm.network.network.observing

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.adityaikhbalm.network.Connectivity
import com.adityaikhbalm.network.ReactiveNetwork.LOG_TAG
import kotlinx.coroutines.flow.Flow

/**
 * Network observing strategy allows to implement different strategies for monitoring network
 * connectivity change. Network monitoring API may differ depending of specific Android version.
 */
abstract class NetworkObservingStrategy {
    // it has to be initialized in the Observable due to Context
    internal lateinit var networkCallback: ConnectivityManager.NetworkCallback

    /**
     * Observes network connectivity
     *
     * @param context of the Activity or an Application
     * @return Observable representing stream of the network connectivity
     */
    abstract fun observeNetworkConnectivity(context: Context): Flow<Connectivity>

    /**
     * Handles errors, which occurred during observing network connectivity
     *
     * @param message to be processed
     * @param exception which was thrown
     */
    internal fun ConnectivityManager.tryToUnregisterCallback() {
        try {
            this.unregisterNetworkCallback(networkCallback)
        } catch (exception: Exception) {
            onError(
                ERROR_MSG_NETWORK_CALLBACK,
                exception
            )
        }
    }

    internal fun onError(
        message: String,
        exception: Exception
    ) {
        Log.e(LOG_TAG, message, exception)
    }

    companion object {
        internal const val ERROR_MSG_NETWORK_CALLBACK: String =
            "could not unregister network callback"
        internal const val ERROR_MSG_RECEIVER: String = "could not unregister receiver"
    }
}
