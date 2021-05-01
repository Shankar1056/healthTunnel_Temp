package com.healthtunnel.data.network

import android.content.Intent
import com.healthtunnel.ui.utility.AppController

object NetworkResponseActionHandler {

    fun showServerError(message: String?) {

    }

    fun onUnAuthenticDo() {

    }

    fun networkErrorPage() {
        try {
            val intent = Intent(AppController.getInstance().applicationContext, NetworkErrorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            AppController.getInstance().startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
