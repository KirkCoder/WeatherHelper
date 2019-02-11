package ru.kcoder.weatherhelper.features.weather.detail

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

interface ContractWeatherDetail {

    abstract class ViewModel(
        scopeHandler: ScopeHandler,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeHandler, errorSupervisor) {
        abstract val weather: LiveData<List<Any>>
        abstract val status: LiveData<Boolean>
        abstract val checked: LiveData<Int>
        abstract fun forceUpdate()
        abstract fun clickInform(position: Int?)
    }

    interface Interactor{
        fun updateWeather(
            whId: Long,
            forceUpdate: Boolean,
            callback: (List<Any>) -> Unit,
            statusCallback: (Boolean) -> Unit
        )
    }
}