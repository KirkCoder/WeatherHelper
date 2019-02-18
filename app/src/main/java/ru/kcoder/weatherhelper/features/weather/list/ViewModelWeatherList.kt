package ru.kcoder.weatherhelper.features.weather.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor

class ViewModelWeatherList(
    private val interactor: ContractWeatherList.Interactor,
    errorSupervisor: ErrorSupervisor
) : ContractWeatherList.ViewModel(interactor, errorSupervisor) {

    override val editStatus = MutableLiveData<Boolean>()
    override val weatherHolders = MediatorLiveData<List<WeatherHolder>>()

    init {
        editStatus.value = false
        weatherHolders.addSource(interactor.getAllWeather()){
            weatherHolders.value = it
        }
    }

    override fun forceUpdate(id: Long) {
        interactor.forceUpdate(id)
    }

    override fun setEditStatus(isEditStatus: Boolean) {
        editStatus.value = isEditStatus
    }

    override fun delete(id: Long, list: List<WeatherHolder>) {
        notifyChange(list)
        interactor.delete(id)
    }

    override fun changedData(list: List<WeatherHolder>) {
        notifyChange(list)
        interactor.changedData(list)
    }

    override fun notifyChange(list: List<WeatherHolder>) {
        weatherHolders.value = list
    }

    override fun onCleared() {
        interactor.clearStatus()
        super.onCleared()
    }
}