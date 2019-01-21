package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

class ViewModelWeatherListImpl(
    private val interactor: WeatherListInteractor
) : ViewModelWeatherList() {

    override val weatherList = MutableLiveData<WeatherModel>()
    override val updateStatus = MutableLiveData<Pair<Long, Boolean>>()
    override val editStatus = MutableLiveData<Boolean>()

    init {
        editStatus.value = false
        getAllWeather()
    }

    private fun getAllWeather() {
        interactor.getAllWeather(viewModelScope, {
            weatherList.value = it
        }, {
            updateStatus.value = it
        }, this::errorCallback)
    }

    override fun addPlace(holder: WeatherHolder) {
        val model = weatherList.value
        if (model != null) {
            val tmpList = mutableListOf<WeatherHolder>()
            val tmpPositionMap = mutableMapOf<Long, Int>()
            val tmpListMap = mutableMapOf<Long, Int>()
            tmpList.addAll(model.list)
            tmpList.add(holder)
            tmpListMap.putAll(model.listMap)
            tmpListMap[holder.id] = tmpList.size - 1
            tmpPositionMap.putAll(model.positionMap)
            tmpPositionMap[holder.id] = holder.position
            weatherList.value = WeatherModel(
                tmpList, tmpListMap, tmpPositionMap, holder.id
            )
        } else {
            weatherList.value = WeatherModel(
                listOf(holder), mapOf(holder.id to 0), mapOf(holder.id to holder.position), holder.id
            )
        }
        forceUpdate(holder.id)
    }

    override fun forceUpdate(id: Long) {
        interactor.forceUpdate(id, viewModelScope, {
            weatherList.value = it
        }, {
            updateStatus.value = Pair(id, false)
            errorCallback(it)
        })
    }

    override fun setEditStatus(isEditStatus: Boolean) {
        editStatus.value = isEditStatus
    }

    override fun delete(id: Long) {
        interactor.delete(id, viewModelScope, {
            weatherList.value = it
        }, this::errorCallback)
    }
}