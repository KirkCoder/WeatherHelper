package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderPresentation

interface WeatherDetailInteractor {
    fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherHolderPresentation) -> Unit,
        statusCallback: (Boolean) -> Unit,
        errorCallback: (Int) -> Unit
    )
}