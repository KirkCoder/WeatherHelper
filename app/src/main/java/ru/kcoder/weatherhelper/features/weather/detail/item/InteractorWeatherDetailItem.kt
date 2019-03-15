package ru.kcoder.weatherhelper.features.weather.detail.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
    scopeHandler: ScopeHandler,
    settingsRepository: SettingsRepository,
    errorSupervisor: ErrorSupervisor
) : BaseInteractor(settingsRepository, scopeHandler, errorSupervisor),
    ContractWeatherDetailItem.Interactor {

    private val liveData = MediatorLiveData<List<Any>>()

    override fun getWeather(id: Long): LiveData<List<Any>> {
        liveData.addSource(repository.getWeather(id, scopeHandler.scope)) { holder ->
            holder?.let { nh ->
                loading({ nh.mapToAnyList() }, {
                    liveData.value = it
                })
            }
        }
        return liveData
    }

    override fun updateWeather(
        id: Long,
        statusCallback: (Boolean) -> Unit
    ) {
        runWithSettings { settings ->
            loadingProgress({
                repository.updateWeatherById(settings, id, scopeHandler.scope)
            }, loadingStatus = statusCallback)
        }
    }

    private fun WeatherHolder.mapToAnyList(): List<Any> {
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
