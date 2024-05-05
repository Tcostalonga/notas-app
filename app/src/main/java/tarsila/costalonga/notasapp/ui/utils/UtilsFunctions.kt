package tarsila.costalonga.notasapp.ui.utils

import android.content.Context
import android.widget.Toast

fun makeToast(
    context: Context,
    msg: String,
) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
