package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import tarsila.costalonga.notasapp.MainActivity
import tarsila.costalonga.notasapp.R
import tarsila.costalonga.notasapp.broadcast.TurnOffNotifBcReceiver
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio

class AlarmViewModel @ViewModelInject constructor(
    @ApplicationContext val context: Context,
    val repositorio: NotasRepositorio
) :
    ViewModel() {

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

    Log.i("createNotification", "notif")

    val notificationIdInt = intent.getLongExtra(NOTIFICATION_ID, 0).toInt()

    val notifBuilder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)

    val intentShowMainActiv = Intent(context, MainActivity::class.java)

    val turnOffIntent = Intent(context, TurnOffNotifBcReceiver::class.java)
    turnOffIntent.putExtra(INTENT_TURN_OFF, notificationIdInt)

    //Esse pending intent é o da ação da notificação - Ao clicar, leva a MainActivity
    val mainActivpendingIntent = PendingIntent.getActivity(
        context,
        notificationIdInt,
        intentShowMainActiv,
        PendingIntent.FLAG_ONE_SHOT
    )

    //Esse pending intent é o do botao acao
    val turnOffPendindIntent = PendingIntent.getBroadcast(
        context,
        notificationIdInt,
        turnOffIntent,
        PendingIntent.FLAG_ONE_SHOT
    )

    val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.icone_notif_nan)

    return notifBuilder.apply {
        setContentTitle(intent.getStringExtra(KEY_NOTIF_TEXT))
            .setContentText(intent.getStringExtra(KEY_NOTIF_ANOTACAO))
            .setDefaults(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.alarm_on_24)
            .setLargeIcon(largeIcon)
            .setContentIntent(mainActivpendingIntent)
            .setAutoCancel(true)
            .setLights(Color.GREEN, 1000, 1000)
            .addAction(
                R.mipmap.ic_launcher,
                context.getString(R.string.dispensar),
                turnOffPendindIntent
            )
            .priority = NotificationCompat.PRIORITY_HIGH
    }.build()
}

