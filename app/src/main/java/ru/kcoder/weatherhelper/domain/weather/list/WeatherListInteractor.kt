package ru.kcoder.weatherhelper.domain.weather.list

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherListInteractor {

    fun getWeatherByCoordinat(lat: Double, lon: Double, callback: (WeatherHolder) -> Unit)

    fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit)? = null)
}