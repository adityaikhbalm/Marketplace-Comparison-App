package com.adityaikhbalm.network.network.observing.strategy

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import com.adityaikhbalm.network.Connectivity
import com.adityaikhbalm.network.network.observing.NetworkObservingStrategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart

/**
 * Network observing strategy for devices with Android Lollipop (API 21) or higher.
 * Uses Network Callback API.
 */
@TargetApi(21)
class LollipopNetworkObservingStrategy : NetworkObservingStrategy() {
    @ExperimentalCoroutinesApi
    override fun observeNetworkConnectivity(context: Context): Flow<Connectivity> {
        val service: String = Context.CONNECTIVITY_SERVICE
        val manager: ConnectivityManager = context.getSystemService(service) as ConnectivityManager
        return callbackFlow<Connectivity> {
            networkCallback = object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    offer(Connectivity.create(context))
                }

                override fun onLost(network: Network) {
                    offer(Connectivity.create(context))
                }
            }

            val networkRequest: NetworkRequest = NetworkRequest.Builder().build()
            manager.registerNetworkCallback(networkRequest, networkCallback)

            awaitClose { manager.tryToUnregisterCallback() }
        }.onStart { emit(Connectivity.create(context)) }.distinctUntilChanged()
    }
}
