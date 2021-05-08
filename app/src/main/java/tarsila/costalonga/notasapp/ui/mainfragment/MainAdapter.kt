package tarsila.costalonga.notasapp.ui.mainfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import tarsila.costalonga.notasapp.bd.Notas
import tarsila.costalonga.notasapp.databinding.ListItemBinding
import tarsila.costalonga.notasapp.utils.setHachura
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(val clickListener: NotasListener) :
    RecyclerView.Adapter<MainAdapter.NotasViewHolder>(), Filterable {

    var listaFixa = listOf<Notas>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    var listaDoFiltro = ArrayList<Notas>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    lateinit var listener: ClicksAcao

    inner class NotasViewHolder constructor(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Notas, clickListener: NotasListener) {
            binding.itemView = item
            binding.showTitulo.setOnClickListener {
                clickListener.onClick(item)
            }
            binding.showCheckbox.setOnCheckedChangeListener { _, b ->
                listener.checkClick(item, b)
                binding.showTitulo.setHachura(b)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val inflater = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotasViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return listaDoFiltro.size
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val item = listaDoFiltro[position]
        holder.bind(item, clickListener)
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList: ArrayList<Notas> = ArrayList<Notas>()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(listaFixa)
                } else {
                    val patternFiltrar =
                        constraint.toString().toLowerCase(Locale.getDefault()).trim()
                    for (item in listaFixa) {
                        if (item.titulo.toLowerCase(Locale.getDefault())
                                .startsWith(patternFiltrar)
                        ) {
                            filteredList.add(item)
                        }
                    }

                }
                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listaDoFiltro.clear()
                listaDoFiltro.addAll(results?.values as Collection<Notas>)
                notifyDataSetChanged()
            }
        }
    }
}

interface ClicksAcao {

    fun checkClick(nota: Notas, boolean: Boolean)
}

class NotasListener(val clickListener: (notasObj: Notas) -> Unit) {
    fun onClick(nota: Notas) = clickListener(nota)

}