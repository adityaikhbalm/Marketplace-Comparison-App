package com.adityaikhbalm.network.network.observing.strategy

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.PowerManager
import com.adityaikhbalm.network.Connectivity
import com.adityaikhbalm.network.network.observing.NetworkObservingStrategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart

/**
 * Network observing strategy for devices with Android Marshmallow (API 23) or higher.
 * Uses Network Callback API and handles Doze mode.
 */
@FlowPreview
@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@TargetApi(23)
class MarshmallowNetworkObservingStrategy : NetworkObservingStrategy() {
    private val connectivitySubject: BroadcastChannel<Connectivity> =
        BroadcastChannel(Channel.CONFLATED)
    private val idleReceiver: BroadcastReceiver = createIdleBroadcastReceiver()
    private var lastConnectivity = Connectivity()

    override fun observeNetworkConnectivity(context: Context): Flow<Connectivity> {
        val service: String = Context.CONNECTIVITY_SERVICE
        val manager: ConnectivityManager = context.getSystemService(service) as ConnectivityManager
        networkCallback = createNetworkCallback(context)
        registerIdleReceiver(context)
        val request: NetworkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                .build()
        manager.registerNetworkCallback(request, networkCallback)
        return callbackFlow {
            connectivitySubject.consumeEach {
                offer(it)
                print("offer")
                lastConnectivity = it
            }
            awaitClose {
                manager.tryToUnregisterCallback()
                context.tryToUnregisterReceiver()
            }
        }.flatMapConcat { connectivity ->
            propagateAnyConnectedState(lastConnectivity, connectivity)
        }.onStart { emit(Connectivity.create(context)) }.distinctUntilChanged()
    }

    private fun propagateAnyConnectedState(
        last: Connectivity,
        current: Connectivity
    ): Flow<Connectivity> {
        val typeChanged: Boolean = last.type != current.type
        val wasConnected: Boolean = last.state == NetworkInfo.State.CONNECTED
        val isDisconnected: Boolean = current.state == NetworkInfo.State.DISCONNECTED
        val isNotIdle: Boolean = current.detailedState != NetworkInfo.DetailedState.IDLE
        return if (typeChanged && wasConnected && isDisconnected && isNotIdle) {
            flowOf(current, last)
        } else {
            flowOf(current)
        }
    }

    private fun registerIdleReceiver(context: Context?) {
        val filter = IntentFilter(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)
        context!!.registerReceiver(idleReceiver, filter)
    }

    private fun createIdleBroadcastReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {
                if (isIdleMode(context)) {
                    onNext(Connectivity())
                } else {
                    onNext(Connectivity.create(context!!))
                }
            }
        }
    }

    internal fun isIdleMode(context: Context?): Boolean {
        val packageName: String? = context?.packageName
        val manager: PowerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        val isIgnoringOptimizations: Boolean = manager.isIgnoringBatteryOptimizations(packageName)
        return manager.isDeviceIdleMode && !isIgnoringOptimizations
    }

    private fun Context.tryToUnregisterReceiver() {
        try {
            this.unregisterReceiver(idleReceiver)
        } catch (exception: Exception) {
            onError(ERROR_MSG_RECEIVER, exception)
        }
    }

    private fun createNetworkCallback(context: Context): NetworkCallback {
        return object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                onNext(Connectivity.create(context))
            }

            override fun onLost(network: Network) {
                onNext(Connectivity.create(context))
            }
        }
    }

    internal fun onNext(connectivity: Connectivity) {
        connectivitySubject.offer(connectivity)
    }
}
