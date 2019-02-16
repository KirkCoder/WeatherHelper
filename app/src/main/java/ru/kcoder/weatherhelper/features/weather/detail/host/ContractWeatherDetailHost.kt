package ru.kcoder.weatherhelper.features.weather.detail.host

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.weather.detail.SelectedItem
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherDetailModel
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController

interface ContractWeatherDetailHost {

    abstract class ViewModel(
        scopeController: ScopeController,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeController, errorSupervisor) {
        abstract val positions: LiveData<WeatherDetailModel>
        abstract val selected: LiveData<Int>
        abstract fun selectedPage(position: Int)
    }

    interface Interactor: ScopeController {
        fun getWeatherHolderPositions(): LiveData<List<WeatherPosition>>
    }
}