package ru.kcoder.weatherhelper.features.weather.list

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

interface ContractWeatherList {

    abstract class ViewModel(
        scopeHandler: ScopeHandler,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeHandler, errorSupervisor) {
        abstract val weatherList: LiveData<WeatherModel>
        abstract val updateStatus: LiveData<Pair<Long, Boolean>>
        abstract val editStatus: LiveData<Boolean>

        abstract fun addPlace(holder: WeatherHolder)
        abstract fun forceUpdate(id: Long)
        abstract fun setEditStatus(isEditStatus: Boolean)
        abstract fun delete(id: Long, list: List<WeatherHolder>)
        abstract fun changedData(list: List<WeatherHolder>)
        abstract fun notifyChange(model: WeatherModel)
    }

    interface Interactor {

        fun getAllWeather(
            callback: (WeatherModel) -> Unit,
            bdUpdateStatus: (Pair<Long, Boolean>) -> Unit
        )

        fun forceUpdate(
            id: Long,
            callback: (WeatherModel) -> Unit,
            onFail: () -> Unit
        )

        fun delete(
            id: Long,
            scope: CoroutineScope
        )

        fun changedData(list: List<WeatherHolder>)

    }
}