package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import tarsila.costalonga.notasapp.MainActivity
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import java.util.*

private const val NOTIFICATION_ID = 0
const val PRIMARY_CHANNEL_ID = "Canal_primario"
const val KEY_NOTIF_TEXT = "Canal_primario"


class AlarmViewModel @ViewModelInject constructor(@ApplicationContext val context: Context) :
    ViewModel() {

    private val _today: Calendar = Calendar.getInstance()
    val today: Calendar
        get() = _today

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    fun createAlarm(notaObj: Notas) {


        val intent = Intent(context, MyAlarmManager::class.java)
        intent.putExtra(KEY_NOTIF_TEXT, notaObj.titulo)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notaObj.dtCriacao.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            _today.timeInMillis,
            pendingIntent
        )
    }

    fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel =
                NotificationChannel(
                    PRIMARY_CHANNEL_ID,
                    "Alarme",
                    NotificationManager.IMPORTANCE_HIGH
                )

            notifChannel.also {
                it.enableVibration(true)
                it.enableLights(true)
                it.lightColor = Color.GREEN
                it.description = "Alarme da nota"
            }
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java) as NotificationManager

            notificationManager.createNotificationChannel(notifChannel)
            Log.i("chegou", "Per")

        }
    }
}

fun NotificationManager.createNotification(context: Context, intent: Intent) {
    val notifBuilder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
    val intentShowMainActiv = Intent(context, MainActivity::class.java)

    //Esse pending intent é o da ação da notificação - Ao clicar, leva a MainActivity
    val pendingIntent = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        intentShowMainActiv,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    notifBuilder.apply {
        this.setContentTitle(intent.getStringExtra(KEY_NOTIF_TEXT))
            .setContentText("Clique aqui para acessar sua nota")
            .setDefaults(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.alarm_on_24)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .priority = NotificationCompat.PRIORITY_HIGH
    }
    notify(NOTIFICATION_ID, notifBuilder.build())
}

fun NotificationManager.cancelNotification() {
    cancelAll()

}