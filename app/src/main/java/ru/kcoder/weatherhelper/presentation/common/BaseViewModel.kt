package ru.kcoder.weatherhelper.presentation.common

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.toolkit.android.LocalException

abstract class BaseViewModel(
    abstractInteractor: BaseInteractor
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        abstractInteractor.initScope(viewModelScope)
        abstractInteractor.initErrorHandler(this::errorCallback)
    }

    open val errorLiveData: MutableLiveData<Int> = SingleLiveData()

    open fun errorCallback(err: LocalException) {
        errorLiveData.value = err.msg.resourceString
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}