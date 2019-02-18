package ru.kcoder.weatherhelper.features.weather.detail.item

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController

interface ContractWeatherDetailItem {

    abstract class ViewModel(
        scopeController: ScopeController,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeController, errorSupervisor) {
        abstract val weather: LiveData<List<Any>>
        abstract val status: LiveData<Boolean>
        abstract val checked: LiveData<Int>
        abstract fun clickInform(position: Int?)

        abstract fun updateWeather()
    }

    interface Interactor: ScopeController{

        fun getWeather(id: Long): LiveData<List<Any>>

        fun updateWeather(
            id: Long,
            statusCallback: (Boolean) -> Unit
        )
    }
}