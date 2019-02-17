package ru.kcoder.weatherhelper.features.weather.list

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController

interface ContractWeatherList {

    abstract class ViewModel(
        scopeController: ScopeController,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeController, errorSupervisor) {
        abstract val weatherList: LiveData<WeatherModel>
        abstract val updateStatus: LiveData<Pair<Long, Boolean>>
        abstract val editStatus: LiveData<Boolean>
        abstract val weatherHolders: LiveData<List<WeatherHolder>>

        abstract fun addPlace(holder: WeatherHolder)
        abstract fun forceUpdate(id: Long)
        abstract fun setEditStatus(isEditStatus: Boolean)
        abstract fun delete(id: Long, list: List<WeatherHolder>)
        abstract fun changedData(list: List<WeatherHolder>)
        abstract fun notifyChange(model: WeatherModel)
        abstract fun notifyChange(list: List<WeatherHolder>)
    }

    interface Interactor: ScopeController {

        fun getAllWeather(
            callback: (WeatherModel) -> Unit,
            bdUpdateStatus: (Pair<Long, Boolean>) -> Unit
        )

        fun forceUpdate(
            id: Long,
            callback: (WeatherModel) -> Unit,
            onFail: () -> Unit
        )

        fun delete(id: Long)

        fun changedData(list: List<WeatherHolder>)

        fun getAllWeatherLd() : LiveData<List<WeatherHolder>>

        fun forceUpdate(id: Long)

        fun clearStatus()
    }
}