package com.ameen.expirydatetracker.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ameen.expirydatetracker.util.NotificationUtil

class CheckDateWorker(context: Context, workParameters: WorkerParameters) :
    CoroutineWorker(context, workParameters) {

    private val TAG = "CheckDateWorker"

    override suspend fun doWork(): Result {
        Log.i(TAG, "checkDaysLeft: doWork invoked")

        NotificationUtil(applicationContext).createNotificationChannel()
        NotificationUtil(applicationContext).createNotification("Title", "Content")

        return Result.success()
    }
}