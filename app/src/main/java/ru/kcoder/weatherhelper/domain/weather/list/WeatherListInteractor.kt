package ru.kcoder.weatherhelper.domain.weather.list

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

interface WeatherListInteractor {

    fun getAllWeather(
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        errorCallback: ((Int) -> Unit)? = null
    )

    fun getMockedWeather(): WeatherHolder

    fun forceUpdate(
        id: Long,
        scope: CoroutineScope,
        callback: (WeatherHolder) -> Unit,
        errorCallback: ((Int) -> Unit)
    )

    fun delete(
        id: Long,
        scope: CoroutineScope,
        callback: () -> Unit,
        errorCallback: ((Int) -> Unit)
    )
}