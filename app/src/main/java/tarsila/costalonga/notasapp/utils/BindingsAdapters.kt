package tarsila.costalonga.notasapp.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:notaFinalizada")
fun TextView.setHachura(enabled: Boolean) {
    if (enabled) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}
