package tarsila.costalonga.notasapp.ui.alarmfragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyAlarmManager : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "oiiii", Toast.LENGTH_SHORT).show()
        Log.i("alarme", "$intent")
    }
}