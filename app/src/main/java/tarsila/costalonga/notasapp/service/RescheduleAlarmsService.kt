package tarsila.costalonga.notasapp.service

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import tarsila.costalonga.notasapp.repositorio.NotasRepositorio
import javax.inject.Inject

@AndroidEntryPoint
class RescheduleAlarmsService :
    LifecycleService() {

    @Inject
    lateinit var repositorio: NotasRepositorio

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val allAlarms = repositorio.getAlarmsOnSuspend()

        allAlarms.observe(this, Observer {
            it.forEachIndexed { index, notas ->
           // AlarmUtils.createAlarm(notas)
             //   Log.i("RescheduleAlarmsService", notas.titulo)
            }
        })
        Log.i("RescheduleAlarmsService", "titulo")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        this.stopSelf()

    }


}

