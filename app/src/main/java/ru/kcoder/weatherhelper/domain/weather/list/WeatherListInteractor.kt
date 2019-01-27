package ru.kcoder.weatherhelper.domain.weather.list

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.presentation.common.BaseInteractor

interface WeatherListInteractor: BaseInteractor {

    fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit)

    fun forceUpdate(
        id: Long,
        callback: (WeatherModel) -> Unit,
        onFail: () -> Unit
    )

    fun delete(
        id: Long,
        scope: CoroutineScope
    )

    fun changedData(
        list: List<WeatherHolder>,
        scope: CoroutineScope
    )
}