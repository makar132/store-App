package com.example.newmobileapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent


class NetworkUtils(private val context: Context) : KoinComponent {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun getNetworkState(): Flow<NetworkConnectionState> = callbackFlow {
        val networkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkConnectionState.Available)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                trySend(NetworkConnectionState.Losing)
            }

            override fun onLost(network: Network) {
                trySend(NetworkConnectionState.Lost)
            }

            override fun onUnavailable() {
                trySend(NetworkConnectionState.Unavailable)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_MMS)) {
                    trySend(NetworkConnectionState.Available)
                }
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties)
            }

            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                super.onBlockedStatusChanged(network, blocked)
            }
        }
        //register callback
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        //unregister
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }

    }.flowOn(Dispatchers.IO)
}
