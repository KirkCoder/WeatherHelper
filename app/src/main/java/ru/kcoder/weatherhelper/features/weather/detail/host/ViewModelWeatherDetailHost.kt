package ru.kcoder.weatherhelper.features.weather.detail.host

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.detail.SelectedItem
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherDetailModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor

class ViewModelWeatherDetailHost(
    interactor: ContractWeatherDetailHost.Interactor,
    id: Long?,
    needUpdate: Boolean,
    errorSupervisor: ErrorSupervisor
) : ContractWeatherDetailHost.ViewModel(interactor, errorSupervisor) {

    override val positions = LiveDataWeatherHost()
    override val selected = MutableLiveData<Int>()

    init {
        positions.addSource(interactor.getWeatherHolderPositions()) { list ->
            positions.value = WeatherDetailModel(
                list,
                id?.let { SelectedItem(list.map { it.id }.indexOf(id), needUpdate) }
            )
        }
    }

    override fun selectedPage(position: Int) {
        selected.value = position
    }
}