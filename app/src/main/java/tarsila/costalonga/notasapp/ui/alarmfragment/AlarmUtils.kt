package tarsila.costalonga.notasapp.ui.alarmfragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import java.util.*
import javax.inject.Inject


class AlarmUtils @Inject constructor(
    @ApplicationContext val context: Context,
     val repositorio: NotasRepositorio,
     val today: Calendar
) {

    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val intent = Intent(context, AlarmBcReceiver::class.java)


    fun createAlarm(notaObj: Notas) {

        intent.putExtra(KEY_NOTIF_TEXT, notaObj.titulo)
        intent.putExtra(KEY_NOTIF_ANOTACAO, notaObj.anotacao)
        intent.putExtra(NOTIFICATION_ID, notaObj.dtCriacao)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notaObj.dtCriacao.toInt(),
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        // if alarm time has already passed, increment day by 1
        if (today.timeInMillis <= System.currentTimeMillis()) {
            today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH) + 1)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            today.timeInMillis,
            pendingIntent
        )

        notaObj.alarmClock = today.timeInMillis
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

        // resetarAlarmClockValue
        notaObj.alarmClock = notaObj.dtCriacao
        repositorio.updateNota(notaObj)


    }
}

