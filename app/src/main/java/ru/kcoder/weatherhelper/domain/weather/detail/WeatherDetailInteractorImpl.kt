package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.AbstractInteractor

class WeatherDetailInteractorImpl(
    private val repository: WeatherRepository,
    settingsRepository: SettingsRepository
) : AbstractInteractor(settingsRepository), WeatherDetailInteractor {

    override fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Boolean) -> Unit
    ) {
        runWithSettings({ settings ->
            loadingProgress({
                repository.getWeather(settings, whId, forceUpdate)
            }, callback, statusCallback)
        })
    }
}