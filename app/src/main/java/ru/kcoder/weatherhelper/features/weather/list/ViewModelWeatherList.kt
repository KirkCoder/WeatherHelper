package ru.kcoder.weatherhelper.features.weather.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.GlobalScope
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor

class ViewModelWeatherList(
    private val interactor: ContractWeatherList.Interactor,
    errorSupervisor: ErrorSupervisor
) : ContractWeatherList.ViewModel(interactor, errorSupervisor) {

    override val weatherList = MutableLiveData<WeatherModel>()
    override val updateStatus = MutableLiveData<Pair<Long, Boolean>>()
    override val editStatus = MutableLiveData<Boolean>()
    override val weatherHolders = MediatorLiveData<List<WeatherHolder>>()

    init {
        editStatus.value = false
//        getAllWeather()
        weatherHolders.addSource(interactor.getAllWeatherLd()){
            weatherHolders.value = it
        }
    }

    private fun getAllWeather() {
        interactor.getAllWeather({
            weatherList.value = it
        }, {
            updateStatus.value = it
        })
    }

    override fun addPlace(holder: WeatherHolder) {
        val model = weatherList.value
        if (model != null) {
            val tmpList = mutableListOf<WeatherHolder>()
            val tmpListMap = mutableMapOf<Long, Int>()
            tmpList.addAll(model.list)
            tmpList.add(holder)
            tmpListMap.putAll(model.listMap)
            tmpListMap[holder.id] = tmpList.size - 1
            weatherList.value = WeatherModel(
                tmpList, tmpListMap, holder.id
            )
        } else {
            weatherList.value = WeatherModel(
                listOf(holder), mapOf(holder.id to 0), holder.id
            )
        }
        forceUpdate(holder.id)
    }

    override fun forceUpdate(id: Long) {
        interactor.forceUpdate(id, {
            weatherList.value = it
        }, {
            updateStatus.value = Pair(id, false)
        })
    }

    override fun setEditStatus(isEditStatus: Boolean) {
        editStatus.value = isEditStatus
    }

    override fun delete(id: Long, list: List<WeatherHolder>) {
        updateWeatherList(list)
        interactor.delete(id)
    }

    override fun changedData(list: List<WeatherHolder>) {
        updateWeatherList(list)
        interactor.changedData(list)
    }

    override fun notifyChange(model: WeatherModel) {
        weatherList.value = model
    }

    override fun notifyChange(list: List<WeatherHolder>) {
        weatherHolders.value = list
    }

    private fun updateWeatherList(list: List<WeatherHolder>) {
        weatherHolders.value = list
//        weatherList.value?.let { model ->
//            model.list = list
//            model.listMap = list.map { it.id to list.indexOf(it) }.toMap()
//            model.updatedWeatherHolderId = WeatherModel.BROADCAST
//            weatherList.value = model
//        }
    }

    override fun onCleared() {
        interactor.clearStatus()
        super.onCleared()
    }
}