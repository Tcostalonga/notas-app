package tarsila.costalonga.notasapp.service

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.ui.alarmfragment.AlarmUtils
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmsService :
    LifecycleService() {

    @Inject
    lateinit var alarmUtils: AlarmUtils


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        //  val allAlarms = alarmUtils.repositorio.getAlarmsOnSuspend()

/*        allAlarms.observe(this, Observer {
            it.forEachIndexed { _, notas ->
                alarmUtils.createAlarm(notas, notas.alarmClock)
                Log.i("RescheduleAlarmsService", notas.titulo)
            }
        })*/

       var soma = 1
        soma += 1
        Log.i("RescheduleAlarmsService", "$soma")
        this.stopSelf()
        Log.i("RescheduleAlarmsService", "stopSelf()")

        return super.onStartCommand(intent, flags, startId)


    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onDestroy() {
        this.stopSelf()
        super.onDestroy()
    }


}

