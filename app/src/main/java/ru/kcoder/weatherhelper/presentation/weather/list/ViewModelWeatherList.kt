package ru.kcoder.weatherhelper.presentation.weather.list

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.domain.weather.list.WeatherModel
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

class ViewModelWeatherList(
    private val listInteractor: WeatherListInteractor
) : BaseViewModel() {

    val weatherList: MutableLiveData<WeatherModel> = MutableLiveData()
    val weather: MutableLiveData<WeatherHolder> = MutableLiveData()

    init {
        listInteractor.getAllWeather {
            weatherList.value = it
        }


//        listInteractor.getWeatherByCoordinat(45.04484, 38.97603) {
//            weather.value = it
//        }
    }
}