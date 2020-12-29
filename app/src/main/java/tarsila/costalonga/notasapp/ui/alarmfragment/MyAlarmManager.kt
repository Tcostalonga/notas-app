package tarsila.costalonga.notasapp.ui.alarmfragment

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import androidx.core.content.ContextCompat
import tarsila.costalonga.notasapp.BuildConfig


const val TAGWAKELOCK = BuildConfig.APPLICATION_ID + ":wakeLock"


@Suppress("DEPRECATION")
class MyAlarmManager : BroadcastReceiver() {

    @SuppressLint("WakelockTimeout")
    override fun onReceive(context: Context, intent: Intent) {
        val pm = context
            .getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            TAGWAKELOCK
        )

        wl.acquire()

        Log.i("alarme", "$intent")

        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.createNotification(context, intent)


        wl.release()
    }
}

