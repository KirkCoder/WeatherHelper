package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherForecast
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

class ViewModelWeatherListImpl(
    private val listInteractor: WeatherListInteractor
) : ViewModelWeatherList() {

    override val weatherListLiveData = MediatorLiveData<WeatherModel>()
    override val weather: MutableLiveData<WeatherForecast> = MutableLiveData()

    init {
        listInteractor.getAllWeather ({
            weatherListLiveData.addSource(it, weatherListLiveData::setValue)
        }, this::errorCallback)
    }
}