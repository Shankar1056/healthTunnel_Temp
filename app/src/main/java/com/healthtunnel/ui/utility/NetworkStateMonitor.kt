package com.healthtunnel.ui.utility

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkStateMonitor : BroadcastReceiver {
    private var mContext: Context? = null
    private var mIsUp: Boolean = false
    private var mListener: Listener? = null

    val isUp: Boolean
        get() {
            val connectivityManager =
                mContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo? = null
            activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    constructor(context: Context) {
        mContext = context
        mIsUp = isUp
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        val upNow = isUp
        if (upNow == mIsUp)
            return
        mIsUp = upNow
        if (mListener != null) {
            mListener!!.onNetworkStateChange(mIsUp)
        }
    }

    interface Listener {
        fun onNetworkStateChange(up: Boolean)
    }
}
