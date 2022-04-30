package com.rohan.reminders.alarm_utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.rohan.reminders.R

class AlarmUtils : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        try {
            val title: String? = intent.getStringExtra("title")
            val description: String? = intent.getStringExtra("description")
            showNotification(context, title ?: "App", description ?: "Reminder Notification")
        } catch (ex: Exception) {
            Log.d("main", "onReceive : ${ex.message}")
        }
    }

    private fun showNotification(context: Context, title: String, description: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "reminder_channel"
        val channelName = "reminder_name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_launcher_background)

        manager.notify(1, builder.build())
    }
}