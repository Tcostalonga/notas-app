package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    // Acionar fab_add
    private var _fabAdd = MutableLiveData<Int>()
    val  fabAdd: LiveData<Int>
        get() = _fabAdd



}