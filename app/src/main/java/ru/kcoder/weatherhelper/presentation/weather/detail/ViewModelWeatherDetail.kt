package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderPresentation
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel
import ru.kcoder.weatherhelper.presentation.common.SingleLiveData

abstract class ViewModelWeatherDetail: BaseViewModel() {
    abstract val weatherPresentation: MutableLiveData<WeatherHolderPresentation>
    abstract val status: MutableLiveData<Boolean>
    abstract fun forceUpdate()
}