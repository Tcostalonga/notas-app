package tarsila.costalonga.notasapp.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import tarsila.costalonga.notasapp.R

fun makeToast(context: Context, msg: String): Unit {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun View.hideKeyboard() {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}


