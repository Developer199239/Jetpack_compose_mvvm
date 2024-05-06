package com.rongcom.newsapp.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import com.rongcom.newsapp.ui.utils.NetworkHelper

class NetworkHelperImpl constructor(private val context: Context) : NetworkHelper {

    override fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork?.isConnected ?: false
    }
}