package com.ameen.expirydatetracker.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ameen.expirydatetracker.R
import com.ameen.expirydatetracker.ui.MainActivity

class NotificationUtil(val context: Context) {

    private val intentOpenExpireFragment = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }

    private val pendingIntent = PendingIntent.getActivity(context, 0, intentOpenExpireFragment, 0)

    fun createNotification(title: String, contentText: String) =
        NotificationCompat.Builder(context, context.getString(R.string.CHANNEL_ID))
            .setSmallIcon(R.drawable.ic_code_scanner)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()


    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.CHANNEL_NAME)
            val descriptionText = context.getString(R.string.CHANNEL_DESCRIPTION)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(
                    context.getString(R.string.CHANNEL_ID),
                    name,
                    importance
                ).apply {
                    description = descriptionText
                }

            // Register Channel with System.
            val notificationManager: NotificationManager =
                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

            notificationManager.createNotificationChannel(channel)

        }

    }
}