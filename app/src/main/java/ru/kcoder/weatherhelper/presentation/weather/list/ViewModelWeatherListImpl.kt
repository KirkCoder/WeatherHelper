package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderFuture
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

class ViewModelWeatherListImpl(
    private val listInteractor: WeatherListInteractor
) : ViewModelWeatherList() {

    override val weatherListLiveData: MutableLiveData<WeatherModel> = MutableLiveData()
    override val weather: MutableLiveData<WeatherHolderFuture> = MutableLiveData()

    init {
        listInteractor.getAllWeather ({
            weatherListLiveData.value = it
        }, this::errorCallback)
    }
}