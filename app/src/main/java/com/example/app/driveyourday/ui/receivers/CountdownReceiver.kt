package com.example.app.driveyourday.ui.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.app.driveyourday.R
import com.example.app.driveyourday.domain.model.Countdown
import com.example.app.driveyourday.ui.MainActivity
import com.example.app.driveyourday.util.getFlagImmutableCompat
import com.example.app.driveyourday.util.getSerializableExtraCompat
import com.example.app.driveyourday.util.toMinutesFromMillis

private const val TAG = "CountdownReceiver"

class CountdownReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context?, intent: Intent?) {
        Log.i(TAG, "onReceive")
        if (ctx != null && intent != null) {
            val countdown = intent.getSerializableExtraCompat(
                EXTRA_COUNTDOWN,
                Countdown::class.java
            )
            requireNotNull(countdown)
            Log.i(TAG, "$countdown")

            val pendingIntent = Intent(ctx, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                // putExtra() //TODO
            }.let { tappedIntent ->
                PendingIntent.getActivity(
                    ctx,
                    0,
                    tappedIntent,
                    FLAG_UPDATE_CURRENT or getFlagImmutableCompat()
                )
            }

            val notification = NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Timer ${countdown.name} finished")
                .setContentText("${countdown.durationMillis.toMinutesFromMillis()} min elapsed")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

            val notificationManager = NotificationManagerCompat.from(ctx)
            createNotificationChannel(notificationManager)
            notificationManager.notify(countdown.id.toInt(), notification)
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManagerCompat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true);
                setLightColor(Color.RED);
                enableVibration(true);
            }
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "COUNTDOWN"
        const val NOTIFICATION_CHANNEL_NAME = "Countdown"
        const val EXTRA_COUNTDOWN = "countdown"
    }
}