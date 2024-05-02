package tarsila.costalonga.notasapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tarsila.costalonga.notasapp.data.local.Notas
import tarsila.costalonga.notasapp.data.repository.NoteDataRepository
import javax.inject.Inject

@HiltViewModel
class EstatisticasViewModel
    @Inject
    constructor(private val repositorio: NoteDataRepository) : ViewModel() {
        private val allNotas = mutableListOf<Notas>()

        private val _totalAtivas = MutableStateFlow(0)
        val totalAtivas = _totalAtivas.asStateFlow()

        internal fun carregarNotas() {
            viewModelScope.launch(Dispatchers.IO) {
                allNotas.addAll(repositorio.getTodasNotas())
            }
            notasAtivas()
        }

        internal fun totalCriadas(): Flow<Int> = flowOf(allNotas.count())

        internal fun notasAtivas() = _totalAtivas.update { allNotas.count { !it.finalizado } }

        internal fun notasFinalizadas(): Flow<Int> = flowOf(allNotas.count { it.finalizado })
    }
