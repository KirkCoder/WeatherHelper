package ru.kcoder.weatherhelper.features.weather.detail.host

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.weather.detail.SelectedItem
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

interface ContractWeatherDetailHost {

    abstract class ViewModel(
        scopeHandler: ScopeHandler,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeHandler, errorSupervisor) {
        abstract val positions: LiveData<List<WeatherPosition>>
        abstract val selectedFirst: LiveData<SelectedItem>
        abstract val selected: LiveData<Int>
        abstract fun selectedPage(position: Int)
    }

    interface Interactor {
        fun getWeatherHolderPositions(): LiveData<List<WeatherPosition>>
    }
}