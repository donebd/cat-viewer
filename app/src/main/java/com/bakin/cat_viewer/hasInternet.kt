package com.bakin.cat_viewer

import android.content.Context
import android.net.ConnectivityManager


fun hasInternet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting && netInfo.isAvailable
}