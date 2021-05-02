package com.healthtunnel.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.healthtunnel.MainActivity
import com.healthtunnel.R
import com.healthtunnel.ui.auth.AuthActivity
import com.healthtunnel.ui.fcm.MyFirebaseMessagingService
import com.healthtunnel.ui.fcm.NotificationModel
import com.healthtunnel.ui.utility.ClsGeneral
import com.healthtunnel.ui.utility.Constant

class SplashScreen : AppCompatActivity() {
    private var notificationModel: NotificationModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_splash)

        handleDeepLinking()

        val mService = MyFirebaseMessagingService()
        mService.composeNotification(this, null)
        Handler().postDelayed({
            if (ClsGeneral.getPreferences(Constant.EMAIL).isNullOrEmpty()) {
                startActivity(Intent(this, AuthActivity::class.java))
            } else {
                startActivity(
                    Intent(this, MainActivity::class.java)
                        .putExtra(MyFirebaseMessagingService.NOTIFICATION_MODEL, notificationModel)
                )
            }

            finish()
        }, 3000)
    }

    private fun handleDeepLinking() {
        if (intent.hasExtra(MyFirebaseMessagingService.NOTIFICATION_MODEL) &&
            (intent.getSerializableExtra(MyFirebaseMessagingService.NOTIFICATION_MODEL) != null)
        ) {
            notificationModel =
                intent.getSerializableExtra(MyFirebaseMessagingService.NOTIFICATION_MODEL) as NotificationModel
            Log.d("Splash", notificationModel.toString())
        }
    }
}