package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class MyAlarmManager : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

         Log.i("alarme", "$intent")

        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createNotification(context, intent)

    }
}