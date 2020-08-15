package tarsila.costalonga.notasapp.ui.mainfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.ListItemBinding


class MainAdapter(val clickListener: NotasListener) :
    RecyclerView.Adapter<MainAdapter.NotasViewHolder>() {

    var data = listOf<Notas>()


    class NotasViewHolder constructor(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notas, clickListener: NotasListener) {
            binding.itemView = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
            itemView.setOnClickListener { clickListener.onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val inflater = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NotasViewHolder(inflater)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }
}

class NotasListener(val clickListener: (notasObj: Notas) -> Unit) {
    fun onClick(nota: Notas) = clickListener(nota)

}