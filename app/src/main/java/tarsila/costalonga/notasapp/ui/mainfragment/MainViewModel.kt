package tarsila.costalonga.notasapp.ui.mainfragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel @ViewModelInject constructor() : ViewModel() {

    // Acionar fab_add
    private var _fabAdd = MutableLiveData<Int>()
    val fabAdd: LiveData<Int>
        get() = _fabAdd


}