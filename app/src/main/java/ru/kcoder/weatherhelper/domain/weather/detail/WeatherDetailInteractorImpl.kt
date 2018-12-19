package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor

class WeatherDetailInteractorImpl(
    private val repository: WeatherRepository
) : BaseInteractor(), WeatherDetailInteractor {

    override fun updateWeather(
        whId: Long,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Int) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loadingProgress(repository, {
            getWeatherById(whId)
        }, { data, error, status ->
            data?.let { callback.invoke(it) }
            error?.let { errorCallback(it.msg.resourceString) }
            statusCallback(status)
        })
    }
}