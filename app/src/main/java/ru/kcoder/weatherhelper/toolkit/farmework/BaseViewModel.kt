package ru.kcoder.weatherhelper.toolkit.farmework

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController

abstract class BaseViewModel(
    private val scopeController: ScopeController,
    errorSupervisor: ErrorSupervisor
) : ViewModel() {

    val errorLiveData = errorSupervisor.errorLiveData

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        scopeController.cancel()
    }
}