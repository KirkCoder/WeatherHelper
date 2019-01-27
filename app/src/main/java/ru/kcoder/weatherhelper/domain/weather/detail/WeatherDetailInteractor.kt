package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.presentation.common.BaseInteractor

interface WeatherDetailInteractor: BaseInteractor {
    fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Boolean) -> Unit
    )
}