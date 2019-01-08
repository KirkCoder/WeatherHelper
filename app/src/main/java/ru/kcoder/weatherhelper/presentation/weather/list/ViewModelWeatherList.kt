package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderFuture
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelWeatherList: BaseViewModel() {
    abstract val weatherListLiveData: MutableLiveData<WeatherModel>
    abstract val weather: MutableLiveData<WeatherHolderFuture>

}