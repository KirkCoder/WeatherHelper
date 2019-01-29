package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.presentation.common.BaseInteractor

interface WeatherDetailInteractor: BaseInteractor {
    fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (List<Any>) -> Unit,
        statusCallback: (Boolean) -> Unit
    )
}