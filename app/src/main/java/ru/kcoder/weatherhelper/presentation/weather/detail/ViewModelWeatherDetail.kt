package ru.kcoder.weatherhelper.presentation.weather.detail

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelWeatherDetail: BaseViewModel() {
    abstract val weather: MutableLiveData<WeatherHolder>
    abstract fun updateWeather(whId: Long)
}