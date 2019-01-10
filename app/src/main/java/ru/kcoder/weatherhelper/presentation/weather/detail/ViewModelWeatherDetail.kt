package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelWeatherDetail: BaseViewModel() {
    abstract val weather: MutableLiveData<WeatherHolder>
    abstract val status: MutableLiveData<Boolean>
    abstract fun forceUpdate()
}