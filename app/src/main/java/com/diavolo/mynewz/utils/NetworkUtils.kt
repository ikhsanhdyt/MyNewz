package com.diavolo.mynewz.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

/**
 * Written with passion by Ikhsan Hidayat on 09/08/2023.
 */
class NetworkUtils @Inject constructor(private val context: Context) {
    fun isInternetAvailable(): Boolean {
        val connectionManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectionManager.activeNetwork ?: return false
        val networkCapabilities = connectionManager.getNetworkCapabilities(activeNetwork)
            ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}