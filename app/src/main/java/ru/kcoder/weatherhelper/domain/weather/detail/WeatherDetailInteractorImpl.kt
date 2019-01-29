package ru.kcoder.weatherhelper.domain.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimDay
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimHour
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
        callback: (List<Any>) -> Unit,
        statusCallback: (Boolean) -> Unit
    ) {
        runWithSettings({ settings ->
            loadingProgress({
                repository.getWeather(settings, whId, forceUpdate).mapToAnyList()
            }, callback, statusCallback)
        })
    }

    private fun WeatherHolder.mapToAnyList(): List<Any> {
        val res = mutableListOf<Any>()
        res.addAll(hours.map { SlimHour(it) })
        for ((i) in days.withIndex()) {
            res.add(
                SlimDay(days[i], nights[i])
            )
        }
        return res
    }
}
