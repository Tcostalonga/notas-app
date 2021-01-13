package tarsila.costalonga.notasapp.ui.alarmfragment

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.BuildConfig
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.service.MyServiceAlarm
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
            startAlarmsService(context, intent)
        }

        wl.release()
    }

    private fun startAlarmsService(context: Context, intent: Intent) {

        /*       intent.putExtra(KEY_NOTIF_TEXT, intent.getStringExtra(KEY_NOTIF_TEXT))
               intent.putExtra(KEY_NOTIF_ANOTACAO, intent.getStringExtra(KEY_NOTIF_ANOTACAO))
               intent.putExtra(NOTIFICATION_ID, intent.getLongExtra(NOTIFICATION_ID, 0))
               createNotification(context, intent)
       */
        val intentService = Intent(context, MyServiceAlarm::class.java)
        intentService.putExtra(KEY_NOTIF_TEXT, intent.getStringExtra(KEY_NOTIF_TEXT))
        intentService.putExtra(KEY_NOTIF_ANOTACAO, intent.getStringExtra(KEY_NOTIF_ANOTACAO))
        intentService.putExtra(NOTIFICATION_ID, intent.getLongExtra(NOTIFICATION_ID, 0))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
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




