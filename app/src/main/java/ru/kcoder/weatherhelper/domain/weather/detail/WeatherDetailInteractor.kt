package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentationHolder

interface WeatherDetailInteractor {
    fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherPresentationHolder) -> Unit,
        statusCallback: (Int) -> Unit,
        errorCallback: (Int) -> Unit
    )
}