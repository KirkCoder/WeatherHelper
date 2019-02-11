package ru.kcoder.weatherhelper.toolkit.farmework

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

abstract class BaseViewModel(
    private val scopeHandler: ScopeHandler,
    errorSupervisor: ErrorSupervisor
) : ViewModel() {

    open val errorLiveData = errorSupervisor.errorLiveData

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        scopeHandler.scope.coroutineContext.cancel()
    }
}