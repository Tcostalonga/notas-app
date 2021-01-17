package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.broadcast.AlarmBcReceiver
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

const val NOTIFICATION_ID = "Id da nota"
const val PRIMARY_CHANNEL_ID = "Canal_primario"
const val KEY_NOTIF_TEXT = "Titulo da nota"
const val KEY_NOTIF_ANOTACAO = "Texto da nota"
const val INTENT_TURN_OFF = "Id para desativar notif. igual ao id da nota"

class AlarmUtils @Inject constructor(
    @ApplicationContext val context: Context,
    val repositorio: NotasRepositorio
) {

    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val intent = Intent(context, AlarmBcReceiver::class.java)

    fun createAlarm(notaObj: Notas, todayParam: Long) {
        var today = todayParam

        intent.putExtra(KEY_NOTIF_TEXT, notaObj.titulo)
        intent.putExtra(KEY_NOTIF_ANOTACAO, notaObj.anotacao)
        intent.putExtra(NOTIFICATION_ID, notaObj.dtCriacao)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notaObj.dtCriacao.toInt(),
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )


        // Se o alarme já passou, add 1 dia para o proximo a ser scheduled.
        if (today <= System.currentTimeMillis()) {
            today += 24 * 60 * 60 * 1000
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            today,
            pendingIntent
        )

        notaObj.alarmClock = today
        repositorio.updateNota(notaObj)
    }

    fun cancelAlarm(notaObj: Notas) {

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notaObj.dtCriacao.toInt(),
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        alarmManager.cancel(pendingIntent)

        // resetar valor de alarmClock
        notaObj.alarmClock = notaObj.dtCriacao
        repositorio.updateNota(notaObj)
    }
}

