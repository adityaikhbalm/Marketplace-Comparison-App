package com.adityaikhbalm.pricehunter

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.adityaikhbalm.network.ReactiveNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    @ObsoleteCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ReactiveNetwork.observeNetworkConnectivity(applicationContext).onEach {
            Log.i("MainActivity", "InternetConnectivity changed on $it")
        }.launchIn(CoroutineScope(Dispatchers.Default))
    }
}
