package tarsila.costalonga.notasapp.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import tarsila.costalonga.notasapp.ui.alarmfragment.INTENT_TURN_OFF

class TurnOffNotifBcReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancel(intent.getIntExtra(INTENT_TURN_OFF, 0))
    }
}