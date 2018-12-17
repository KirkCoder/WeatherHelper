package ru.kcoder.weatherhelper.presentation.weather.list

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.domain.weather.list.WeatherModel

class ViewModelWeatherListImpl(
    private val listInteractor: WeatherListInteractor
) : ViewModelWeatherList() {

    override val weatherListLiveData: MutableLiveData<WeatherModel> = MutableLiveData()
    override val weather: MutableLiveData<WeatherHolder> = MutableLiveData()

    init {
        listInteractor.getAllWeather ({
            weatherListLiveData.value = it
        }, this::errorCallback)


//        listInteractor.getWeatherByCoordinat(45.04484, 38.97603) {
//            weather.value = it
//        }
    }
}