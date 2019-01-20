package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

class ViewModelWeatherListImpl(
    private val interactor: WeatherListInteractor
) : ViewModelWeatherList() {

    override val weatherList = MutableLiveData<WeatherModel>()
    override val weatherUpdate = MutableLiveData<WeatherHolder>()
    override val updateStatus = MutableLiveData<Pair<Long, Boolean>>()

    init {
        getAllWeather()
    }

    private fun getAllWeather() {
        interactor.getAllWeather(viewModelScope, {
            weatherList.value = it
        }, {
            updateStatus.value = it
        }, this::errorCallback)
    }

    override fun addPlace(id: Long) {
        val weatherHolder = interactor.getMockedWeather()
        weatherHolder.id = id
        val model = weatherList.value
        if (model != null) {
            val tmpList = mutableListOf<WeatherHolder>()
            val tmpMap = model.map as MutableMap
            tmpList.addAll(model.list)
            tmpList.add(weatherHolder)
            tmpMap[id] = tmpList.size - 1
            weatherList.value = WeatherModel(
                tmpList, tmpMap, id
            )
        } else {
            weatherList.value = WeatherModel(
                listOf(weatherHolder), mapOf(id to 0), id
            )
        }
        forceUpdate(id)
    }

    override fun forceUpdate(id: Long) {
        interactor.forceUpdate(id, viewModelScope, {
            getAllWeather()
            weatherUpdate.value = it
        }, {
            updateStatus.value = Pair(id, false)
            errorCallback(it)
        })
    }
}