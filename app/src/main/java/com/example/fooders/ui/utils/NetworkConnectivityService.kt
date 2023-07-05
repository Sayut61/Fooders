package com.example.fooders.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine

interface INetworkConnectivityService {
    var isOnline: Boolean
    var isOverWifi: Boolean
    var isOverCellular: Boolean
    var isOverEthernet: Boolean
    fun checkNetworkConnection(): Flow<Boolean>
    fun checkWifi(): Flow<Boolean>
    fun checkCellular(): Flow<Boolean>
    fun checkEthernet(): Flow<Boolean>
}

class NetworkConnectivityService(context: Context) : INetworkConnectivityService {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override var isOnline = false
        get() {
            checkConnection()
            return field
        }

    override var isOverWifi = false
        get() {
            checkConnection()
            return field
        }

    override var isOverCellular = false
        get() {
            checkConnection()
            return field
        }

    override var isOverEthernet = false
        get() {
            checkConnection()
            return field
        }

    override fun checkNetworkConnection(): Flow<Boolean> =
        checkWifi()
            .combine(checkCellular()) { wifi, cellular -> wifi || cellular }
            .combine(checkEthernet()) { wifiAndCellular, ethernet -> wifiAndCellular || ethernet }

    override fun checkWifi(): Flow<Boolean> = createFlowForNetworkType(NetworkCapabilities.TRANSPORT_WIFI)

    override fun checkCellular(): Flow<Boolean> = createFlowForNetworkType(NetworkCapabilities.TRANSPORT_CELLULAR)

    override fun checkEthernet(): Flow<Boolean> = createFlowForNetworkType(NetworkCapabilities.TRANSPORT_ETHERNET)

    private fun checkConnection() {

        val networkAvailability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (networkAvailability != null &&
            networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        ) {
            // Если сюда попали то какая-то сеть есть
            isOnline = true
            isOverWifi = networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            isOverCellular = networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            isOverEthernet = networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            isOnline = false
            isOverWifi = false
            isOverCellular = false
            isOverEthernet = false
        }
    }

    private fun createFlowForNetworkType(type: Int) = callbackFlow<Boolean> {

        trySend(false)

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(type)
            .build()

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onUnavailable() {
                trySend(false)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                // этот момент не интересен
            }

            override fun onAvailable(network: Network) {
                trySend(true)
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, callback)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }

}