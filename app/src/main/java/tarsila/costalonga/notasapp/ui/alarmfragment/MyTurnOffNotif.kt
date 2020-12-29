package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class MyTurnOffNotif : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancel(intent.getIntExtra(INTENT_TURN_OFF, 12))


        Log.i("MyTurnOffNotif", "${intent.getIntExtra(INTENT_TURN_OFF, 12)}")
    }
}