package tarsila.costalonga.notasapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun makeToast(context: Context, msg: String): Unit {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        .show()
}

fun View.hideKeyboard() {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)

}


fun horaFormatada(hora: Long): Int {
    val horaFormatada = SimpleDateFormat("HH", Locale.getDefault())
    return horaFormatada.format(hora).toInt()
}

fun minFormatada(min: Long): Int {
    val minFormatada = SimpleDateFormat("mm", Locale.getDefault())
    return minFormatada.format(min).toInt()

}

fun dataFormatada(data: Long): String {
    val dtFormatada = SimpleDateFormat.getDateInstance(3).format(data)
    val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(data)

    return "$dtFormatada, $dayOfWeek"

}



