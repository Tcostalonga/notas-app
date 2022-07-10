package tarsila.costalonga.notasapp.utils

import android.graphics.Paint
import android.widget.TextView

fun TextView.setHachura(enabled: Boolean) {
    paintFlags = if (enabled) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}
