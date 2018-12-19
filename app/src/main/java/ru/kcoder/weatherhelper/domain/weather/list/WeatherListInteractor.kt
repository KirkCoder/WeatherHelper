package ru.kcoder.weatherhelper.domain.weather.list

interface WeatherListInteractor {

    fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit)? = null)
}