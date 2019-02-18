package ru.kcoder.weatherhelper.features.weather.list

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController

interface ContractWeatherList {

    abstract class ViewModel(
        scopeController: ScopeController,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeController, errorSupervisor) {

        abstract val editStatus: LiveData<Boolean>
        abstract val weatherHolders: LiveData<List<WeatherHolder>>

        abstract fun forceUpdate(id: Long)
        abstract fun setEditStatus(isEditStatus: Boolean)
        abstract fun delete(id: Long, list: List<WeatherHolder>)
        abstract fun changedData(list: List<WeatherHolder>)
        abstract fun notifyChange(list: List<WeatherHolder>)
    }

    interface Interactor: ScopeController {

        fun delete(id: Long)

        fun changedData(list: List<WeatherHolder>)

        fun getAllWeather() : LiveData<List<WeatherHolder>>

        fun forceUpdate(id: Long)

        fun clearStatus()
    }
}