package ru.kcoder.weatherhelper.features.weather.detail.item

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimDay
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimHour
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.toolkit.farmework.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

class InteractorWeatherDetailItem(
    private val repository: WeatherRepository,
    settingsRepository: SettingsRepository,
    scopeHandler: ScopeHandler,
    errorSupervisor: ErrorSupervisor
) : BaseInteractor(settingsRepository, scopeHandler, errorSupervisor),
    ContractWeatherDetailItem.Interactor {

    override fun updateWeather(
        whId: Long,
        forceUpdate: Boolean,
        callback: (List<Any>) -> Unit,
        statusCallback: (Boolean) -> Unit
    ) {
        runWithSettings { settings ->
            loadingProgress({
                repository.getWeather(settings, whId, forceUpdate).mapToWeatherDetail()
            }, callback, statusCallback)
        }
    }

    private fun WeatherHolder.mapToWeatherDetail(): List<Any> {
        val res = mutableListOf<Any>()
        res.addAll(hours.map { SlimHour(it) })
        if (res.size > 1 && res[0] is SlimHour) (res[0] as SlimHour).isChecked = true
        res.add(repository.getDayTitle())
        for ((i) in days.withIndex()) {
            res.add(
                SlimDay(
                    days[i],
                    nights[i],
                    "${days[i].tempNow} / ${nights[i].tempNow}${nights[i].degreeThumbnail}"
                )
            )
        }
        return res
    }
}
