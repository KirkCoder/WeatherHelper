package ru.kcoder.weatherhelper.domain.weather.list

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils

class WeatherListInteractorImpl(
    private val repository: WeatherRepository,
    private val settingsRepository: SettingsRepository
) : BaseInteractor(), WeatherListInteractor {

    @Volatile
    private var updatingId: Long? = null

    private var settings: Settings? = null
        @WorkerThread
        get() {
            if (field == null) {
                field = settingsRepository.getSettings()
            }
            return field
        }

    override fun getAllWeather(
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        errorCallback: ((Int) -> Unit)?
    ) {
        loading(repository, scope, {
            getAllWeather().also {
                findNotUpdatedItem(it)
            }
        }, { data, error ->
            data?.let {
                callback(it)
                updateWeatherUnit(scope, callback, bdUpdateStatus)
            }
            error?.let {
                errorCallback?.invoke(it.msg.resourceString)
            }
        })
    }

    override fun forceUpdate(
        id: Long,
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit)
    ) {
        loading(repository, scope, {
            getWeatherById(id)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    private fun updateWeatherUnit(
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit
    ) {
        val id = updatingId
        if (id != null) {
            bdUpdateStatus(Pair(id, true))
            loading(repository, scope, {
                getWeatherById(id)
            }, { data, error ->
                data?.let {
                    updatingId = null
                    callback(it)
                }
                error?.let {
                    log(it.message ?: it.toString())
                    bdUpdateStatus(Pair(id, false))
                    updateWeatherUnit(scope, callback, bdUpdateStatus)
                }
            })
        }
    }

    override fun delete(
        id: Long, scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loading(repository, scope, {
            delete(id)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    @WorkerThread
    private fun findNotUpdatedItem(model: WeatherModel) {
        for (holder in model.list) {
            val data = holder.hours
            val updateTime = settings?.updateTime
            if (!data.isNullOrEmpty()
                && updateTime != null
                && TimeUtils.isHourDifference(data[0].timeLong - holder.timeUTCoffset, updateTime)
            ) {
                updatingId = data[0].holderId
            }
        }
    }
}