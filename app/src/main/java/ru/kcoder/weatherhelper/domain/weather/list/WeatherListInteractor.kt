package ru.kcoder.weatherhelper.domain.weather.list

import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

interface WeatherListInteractor {

    fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit)? = null)
}