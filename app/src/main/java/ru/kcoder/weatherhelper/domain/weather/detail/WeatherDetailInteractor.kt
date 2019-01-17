package ru.kcoder.weatherhelper.domain.weather.detail

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherDetailInteractor {
    fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Boolean) -> Unit,
        errorCallback: (Int) -> Unit,
        scope: CoroutineScope
    )
}