package com.ameen.expirydatetracker.worker

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ameen.expirydatetracker.util.NotificationUtil

class CheckDateWorker(context: Context, workParameters: WorkerParameters) :
    CoroutineWorker(context, workParameters) {

    private val TAG = "CheckDateWorker"

    override suspend fun doWork(): Result {
        Log.i(TAG, "checkDaysLeft: doWork invoked")
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        NotificationUtil(applicationContext).createNotificationChannel()

        val notificationToShow =
            NotificationUtil(applicationContext).createNotification("Title", "Content")

        with(NotificationManagerCompat.from(applicationContext)){
            notify(1, notificationToShow)
        }
    }
}