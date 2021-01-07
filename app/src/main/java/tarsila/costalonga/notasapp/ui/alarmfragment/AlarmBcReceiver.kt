package tarsila.costalonga.notasapp.ui.alarmfragment

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import tarsila.costalonga.notasapp.BuildConfig
import tarsila.costalonga.notasapp.service.MyServiceAlarm
import tarsila.costalonga.notasapp.service.RescheduleAlarmsService


const val TAGWAKELOCK = BuildConfig.APPLICATION_ID + ":wakeLock"


@Suppress("DEPRECATION")
class AlarmBcReceiver : BroadcastReceiver() {

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

            rescheduleAlarmsService(context)
        } else {
            startAlarmsService(context, intent)

            Log.i(AlarmBcReceiver::class.java.name, "chegou no Bdcast")

        }

        wl.release()
    }

    private fun startAlarmsService(context: Context, intent: Intent) {
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

    private fun rescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmsService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }


}
