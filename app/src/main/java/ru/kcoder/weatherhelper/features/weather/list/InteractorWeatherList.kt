package ru.kcoder.weatherhelper.features.weather.list

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.toolkit.farmework.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils

class InteractorWeatherList(
    private val repository: WeatherRepository,
    settingsRepository: SettingsRepository,
    scopeHandler: ScopeHandler,
    errorSupervisor: ErrorSupervisor
) : BaseInteractor(settingsRepository, scopeHandler, errorSupervisor),
    ContractWeatherList.Interactor
{

    @Volatile
    private var updatingId: Long? = null

    override fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit
    ) {
        runWithSettings { settings ->
            loading({
                repository.getAllWeather(settings).also { findNotUpdatedItem(settings, it) }
            }, {
                callback(it)
                updateWeatherUnit(callback, bdUpdateStatus)
            }, {
                updateWeatherUnit(callback, bdUpdateStatus)
            })
        }
    }

    override fun forceUpdate(
        id: Long,
        callback: (WeatherModel) -> Unit,
        onFail: () -> Unit
    ) {
        runWithSettings { settings ->
            loading({
                repository.updateWeatherById(settings, id)
            }, callback, {
                onError(it)
                onFail.invoke()
            })
        }
    }

    private fun updateWeatherUnit(
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit
    ) {
        runWithSettings { settings ->
            val id = updatingId
            if (id != null) {
                bdUpdateStatus(Pair(id, true))
                loading({
                    repository.updateWeatherById(settings, id)
                        .also { findNotUpdatedItem(settings, it) }
                }, {
                    callback(it)
                    checkForUpdate(callback, bdUpdateStatus, id)
                }, {
                    updatingId = null
                    bdUpdateStatus(Pair(id, false))
                })
            }
        }
    }

    private fun checkForUpdate(
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        lustUpdateId: Long
    ) {
        if (updatingId != lustUpdateId) updateWeatherUnit(callback, bdUpdateStatus)
    }

    override fun delete(
        id: Long, scope: CoroutineScope
    ) {
        uploading({ repository.delete(id) }, scope)
    }

    override fun changedData(list: List<WeatherHolder>) {
        uploading({ repository.changedData(list) })
    }

    @WorkerThread
    private fun findNotUpdatedItem(settings: Settings, model: WeatherModel) {
        for (holder in model.list) {
            val data = holder.hours
            val updateTime = settings.updateTime
            if (!data.isNullOrEmpty()
                && TimeUtils.isHourDifference(data[0].timeLong - holder.timeUTCoffset, updateTime)
            ) {
                updatingId = data[0].holderId
            }
        }
    }
}