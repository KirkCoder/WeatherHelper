package ru.kcoder.weatherhelper.presentation.common

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel: ViewModel() {

    private val viewModelJob = SupervisorJob()
    protected open val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    open val errorLiveData: MutableLiveData<Int> = SingleLiveData()

    open fun errorCallback(resErr: Int){
        errorLiveData.value = resErr
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}