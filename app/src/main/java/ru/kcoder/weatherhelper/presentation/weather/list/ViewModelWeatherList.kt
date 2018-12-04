package ru.kcoder.weatherhelper.presentation.weather.list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor

class ViewModelWeatherList(
    private val listInteractor: WeatherListInteractor
): ViewModel() {

    val weatherList: MutableLiveData<List<WeatherHolder>> = MutableLiveData()
    val weather: MutableLiveData<WeatherHolder> = MutableLiveData()

    init {
        listInteractor.getAllWeather {
            weatherList.value = it
        }
        listInteractor.getWeatherByCoordinat(35.0, 139.0){
            weather.value = it
        }
    }
}