package com.Legacy.ofthe.Nile.ui.loading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingViewModel:ViewModel() {

    val progressLD = MutableLiveData<Int>()
    val loadingFinished = MutableLiveData<Any>()
    init {
        var progress = 0
        viewModelScope.launch {
            while (progress<=100){
                delay(20)
                progressLD.postValue(progress)
                progress++
            }
            loadingFinished.postValue(Unit)
        }
    }

}