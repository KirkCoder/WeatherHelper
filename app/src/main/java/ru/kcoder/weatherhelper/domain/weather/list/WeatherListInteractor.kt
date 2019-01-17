package ru.kcoder.weatherhelper.domain.weather.list

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel

interface WeatherListInteractor {

    fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        scope: CoroutineScope,
        errorCallback: ((Int) -> Unit)? = null
    )

    fun getMockedWeather(): WeatherHolder

    fun forceUpdate(
        id: Long,
        callback: (WeatherHolder?, Boolean?) -> Unit,
        scope: CoroutineScope,
        errorCallback: ((Int) -> Unit)
    )
}