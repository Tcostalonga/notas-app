package tarsila.costalonga.notasapp.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.tasks.Task
import java.text.SimpleDateFormat


/*@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Task>?) {
    items?.let {
        (listView.adapter as TasksAdapter).submitList(items)
    }
}*/

@BindingAdapter("app:notaFinalizada")
fun TextView.setHachura(enabled: Boolean) {
    if (enabled) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

/*
@BindingAdapter("app:dtCriacaoFormat")
fun TextView.dtCFormat(dt_criação: Long) {

    text =
        SimpleDateFormat.getDateTimeInstance(3, 3).format(arguments.dt_criacao)
}*/
