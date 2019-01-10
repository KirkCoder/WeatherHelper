package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor

class WeatherDetailInteractorImpl(
    private val repository: WeatherRepository
) : BaseInteractor(), WeatherDetailInteractor {

    override fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Boolean) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loadingProgress(repository, {
            getWeather(whId, forceUpdate)
        }, { data, error, status ->
            data?.let { callback.invoke(it) }
            error?.let { errorCallback(it.msg.resourceString) }
            statusCallback(status)
        })
    }
}