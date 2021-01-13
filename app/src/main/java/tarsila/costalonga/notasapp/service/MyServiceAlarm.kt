package tarsila.costalonga.notasapp.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import tarsila.costalonga.notasapp.ui.alarmfragment.createNotification

class MyServiceAlarm : Service() {

    lateinit var mp: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
         return null
    }

    override fun onCreate() {
        super.onCreate()

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        mp = MediaPlayer.create(this, notification)

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val notification = createNotification(this, intent)

        startForeground(1, notification)

        mp.start()

        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        super.onDestroy()
        mp.stop()
    }

}



