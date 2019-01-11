package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherForecast
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelWeatherList: BaseViewModel() {
    abstract val weatherListLiveData: LiveData<WeatherModel>
    abstract val weather: LiveData<WeatherForecast>

}