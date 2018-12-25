package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentationHolder
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel
import ru.kcoder.weatherhelper.presentation.common.SingleLiveData

abstract class ViewModelWeatherDetail: BaseViewModel() {
    abstract val weather: MutableLiveData<WeatherPresentationHolder>
    abstract val status: SingleLiveData<Int>
    abstract fun updateWeather(whId: Long, forceUpdate: Boolean)
}