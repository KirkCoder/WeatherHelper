package ru.kcoder.weatherhelper.domain.weather.list

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

interface WeatherListInteractor {

    fun getAllWeather(
        callback: (LiveData<WeatherModel>) -> Unit,
        errorCallback: ((Int) -> Unit)? = null)
}