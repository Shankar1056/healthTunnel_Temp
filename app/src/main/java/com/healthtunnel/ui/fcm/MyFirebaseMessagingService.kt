package com.healthtunnel.ui.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.healthtunnel.R
import com.healthtunnel.ui.SplashScreen
import java.io.Serializable
import java.util.*

class MyFirebaseMessagingService : FirebaseMessagingService() {
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        composeNotification(this, remoteMessage)
    }

    private fun composeNotification(mContext: Context?, remoteMessage: RemoteMessage?) {
        if (remoteMessage != null) {
            val message = remoteMessage.notification?.body.toString()
            val title = remoteMessage.notification?.title.toString()
            val imageUrl = remoteMessage.notification?.imageUrl.toString()
            val categoryId = remoteMessage.data.getValue(CATEGORY_ID)
            val subCategoryId = remoteMessage.data.getValue(SUB_CATEGORY_ID)
            val subSubCategoryId = remoteMessage.data.getValue(SUB_SUB_CATEGORY_ID)
            val model = NotificationModel(
                true,
                title,
                message,
                categoryId,
                subCategoryId,
                subSubCategoryId,
                imageUrl
            )
            sendNotification(mContext, model)
            Log.d(TAG, "onMessageReceived: $model")
        }
    }

    /*
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Device New Token: $token")
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message FCM message body received.
     */
    private fun sendNotification(mContext: Context?, model: NotificationModel?) {
        Glide.with(mContext!!).asBitmap()
            .load(model?.explanatoryImageUrl)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    mBitmap: Bitmap,
                    @Nullable transition: Transition<in Bitmap?>?
                ) {

                    sendNotification(mContext, model, mBitmap)
                }

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {
                    sendNotification(mContext, model, null)
                }
            })

    }

    fun sendNotification(mContext: Context?, model: NotificationModel?, largeIcon: Bitmap?) {
        Log.d(TAG, "Notification Title: ${model?.title} & Message: ${model?.message}")
        val timeInMilliSec = Calendar.getInstance().timeInMillis
        val notificationId = timeInMilliSec.toInt()
        val intent = Intent(mContext, SplashScreen::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(NOTIFICATION_MODEL, model)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            mContext, notificationId, intent, PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(
                mContext!!,
                mContext.resources.getString(R.string.default_notification_channel_id)
            )
                .setSmallIcon(R.drawable.logo_health_lunnel)//Transparent Logo
                .setLargeIcon(largeIcon)
                .setContentTitle(model?.title)
                .setContentText(model?.message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setTicker(model?.message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(largeIcon).bigLargeIcon(null)
                )
                .setStyle(NotificationCompat.BigTextStyle().bigText(model?.message))

        val mNotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel =
                NotificationChannel(
                    mContext.resources.getString(R.string.default_notification_channel_id),
                    mContext.resources.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            mNotificationManager.createNotificationChannel(mChannel)
        }
        mNotificationManager.notify(notificationId, notificationBuilder.build())
    }

    companion object {
        const val TAG = "PushNotification"
        const val NOTIFICATION_MODEL = "Notification_Model"
        const val CATEGORY_ID = "ccategoryId"
        const val SUB_CATEGORY_ID = "subCategoryId"
        const val SUB_SUB_CATEGORY_ID = "subSubCategoryId"
    }
}

class NotificationModel(
    val isNotification: Boolean? = false,
    val title: String? = "",
    val message: String? = "",
    val categoryId: String? = "",
    val subCategoryId: String? = "",
    val subSubCategoryId: String? = "",
    val explanatoryImageUrl: String? = ""
) : Serializable {
    override fun toString(): String {
        return "NotificationModel(isNotification=$isNotification, title=$title, message=$message, category=$categoryId, subCategory=$subCategoryId, subSubCategory=$subSubCategoryId)"
    }
}
