package tarsila.costalonga.notasapp.broadcast

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.BuildConfig
import tarsila.costalonga.notasapp.bd.Notas
import javax.inject.Inject

const val TAGWAKELOCK = BuildConfig.APPLICATION_ID + ":wakeLock"

@Suppress("DEPRECATION")
@AndroidEntryPoint
class AlarmBcReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmUtils: AlarmUtils

    private var uiScope = CoroutineScope(Dispatchers.Main)
    private var allNotasAlarm: List<Notas> = arrayListOf()

    @SuppressLint("WakelockTimeout")
    override fun onReceive(context: Context, intent: Intent) {

        Log.i("AlarmBcReceiver", "Entrou no onReceiveBdc")

        val pm = context
            .getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            TAGWAKELOCK
        )

        wl.acquire()

        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {

            startAlarmsReschedule()

        } else {
            Log.i("AlarmBcReceiver", "chegou no metodo do Bcreceiver")
            startAlarmsService(context, intent)
        }

        wl.release()
    }

    private fun startAlarmsService(context: Context, intent: Intent) {

        intent.putExtra(KEY_NOTIF_TEXT, intent.getStringExtra(KEY_NOTIF_TEXT))
        intent.putExtra(KEY_NOTIF_ANOTACAO, intent.getStringExtra(KEY_NOTIF_ANOTACAO))
        intent.putExtra(NOTIFICATION_ID, intent.getLongExtra(NOTIFICATION_ID, 0))
        Log.i("AlarmBcReceiver", "criar notif")

        val notificationManager =
            ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager

        val n = createNotification(context, intent)
        notificationManager.notify(intent.getLongExtra(NOTIFICATION_ID, 0).toInt(), n)
        Log.i("AlarmBcReceiver", "criou e exibiu notif")

    }

    private fun startAlarmsReschedule() {

        uiScope.launch {
            allNotasAlarm = alarmUtils.repositorio.getAlarmsReschedule()
            allNotasAlarm.forEachIndexed { index, notas ->
                Log.i("RescheduleAlarmsService", "${allNotasAlarm[index]}")
                alarmUtils.createAlarm(notas, notas.alarmClock)
            }
        }
    }
}




