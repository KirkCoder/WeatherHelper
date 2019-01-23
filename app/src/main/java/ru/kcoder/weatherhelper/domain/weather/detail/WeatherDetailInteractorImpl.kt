package ru.kcoder.weatherhelper.domain.weather.detail

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor

class WeatherDetailInteractorImpl(
    private val repository: WeatherRepository,
    settingsRepository: SettingsRepository
) : BaseInteractor(settingsRepository), WeatherDetailInteractor {

    override fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (WeatherHolder) -> Unit,
        statusCallback: (Boolean) -> Unit,
        errorCallback: (Int) -> Unit,
        scope: CoroutineScope
    ) {

        runWithSettings(scope, errorCallback) { settings ->
            loadingProgress(repository, scope, {
                getWeather(settings, whId, forceUpdate)
            }, { data, error, status ->
                data?.let { callback.invoke(it) }
                error?.let { errorCallback(it.msg.resourceString) }
                statusCallback(status)
            })
        }
    }
}