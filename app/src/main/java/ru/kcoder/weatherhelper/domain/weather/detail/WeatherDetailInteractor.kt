package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherDetailInteractor {
    fun updateWeather(
        whId: Long,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Int) -> Unit,
        errorCallback: (Int) -> Unit
    )
}