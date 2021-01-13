package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import tarsila.costalonga.notasapp.MainActivity
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio

const val NOTIFICATION_ID = "Id de cada notif"
const val PRIMARY_CHANNEL_ID = "Canal_primario"
const val KEY_NOTIF_TEXT = "Texto_titulo"
const val KEY_NOTIF_ANOTACAO = "Texto_anotacao"
private const val REQUEST_CODE_TURNOFF = 0
const val INTENT_TURN_OFF = "Desativar notificação"

class AlarmViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    val repositorio: NotasRepositorio
) :
    ViewModel() {


    fun checkAlarmsOn(notaObj: Notas): Boolean {
        return (notaObj.dtCriacao != notaObj.alarmClock)
    }

    fun createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel =
                NotificationChannel(
                    PRIMARY_CHANNEL_ID,
                    context.getString(R.string.alarme_notanota),
                    NotificationManager.IMPORTANCE_HIGH
                )

            notifChannel.also {
                it.enableVibration(true)
                it.enableLights(true)
                it.lightColor = Color.GREEN
                it.description = context.getString(R.string.alarme_notanota)
            }
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java) as NotificationManager

            notificationManager.createNotificationChannel(notifChannel)
        }
    }
}

fun createNotification(context: Context, intent: Intent): Notification {

    val notificationIdInt = intent.getLongExtra(NOTIFICATION_ID, 0).toInt()

    val notifBuilder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)

    val intentShowMainActiv = Intent(context, MainActivity::class.java)

    val turnOffIntent = Intent(context, MyTurnOffNotif::class.java)
    turnOffIntent.putExtra(INTENT_TURN_OFF, notificationIdInt)

    //Esse pending intent é o da ação da notificação - Ao clicar, leva a MainActivity
    val pendingIntent = PendingIntent.getActivity(
        context,
        notificationIdInt,
        intentShowMainActiv,
        PendingIntent.FLAG_ONE_SHOT
    )

    //Esse pending intent é o do botao acao
    val turnOffPendindIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE_TURNOFF,
        turnOffIntent,
        PendingIntent.FLAG_ONE_SHOT
    )
    return notifBuilder.apply {
        setContentTitle(intent.getStringExtra(KEY_NOTIF_TEXT))
            .setContentText(intent.getStringExtra(KEY_NOTIF_ANOTACAO))
            .setDefaults(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.alarm_on_24)
            .setAutoCancel(true)
            .setLights(Color.GREEN, 1000, 1000)
            .setContentIntent(pendingIntent)
            .addAction(1, context.getString(R.string.dispensar), turnOffPendindIntent)
            .priority = NotificationCompat.PRIORITY_HIGH
    }.build()

}

