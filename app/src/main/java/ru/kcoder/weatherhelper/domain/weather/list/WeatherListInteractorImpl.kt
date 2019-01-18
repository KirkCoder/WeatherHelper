package ru.kcoder.weatherhelper.domain.weather.list

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
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
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        scope: CoroutineScope,
        errorCallback: ((Int) -> Unit)?
    ) {
        loading(repository, scope, {
            getAllWeather().also {
                createUpdatingList(it)
            }
        }, { data, error ->
            data?.let {
                callback(it)
                updateWeatherUnit(callback, bdUpdateStatus, scope)
            }
            error?.let {
                errorCallback?.invoke(it.msg.resourceString)
            }
        })
    }

    override fun getMockedWeather(): WeatherHolder {
        return repository.getMockedWeather()
    }

    override fun forceUpdate(
        id: Long,
        callback: (WeatherHolder?, Boolean?) -> Unit,
        scope: CoroutineScope,
        errorCallback: ((Int) -> Unit)
    ) {
        loadingProgress(repository, scope, {
            getWeatherById(id)
        }, { data, error, isLoading ->
            data?.let { callback(it, null) }
            callback(null, isLoading)
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    private fun updateWeatherUnit(
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        scope: CoroutineScope
    ) {
        val id = updatingId
        if (id != null) {
            bdUpdateStatus(Pair(id, true))
            loading(repository, scope, {
                getWeatherById(id)
            }, { data, error ->
                data?.let {
                    updatingId = null
                    getAllWeather({ wm ->
                        bdUpdateStatus(Pair(id, false))
                        callback(wm.apply { updatedWeatherHolderId = it.id })
                    }, bdUpdateStatus, scope)
                }
                error?.let {
                    log(it.message ?: it.toString())
                    bdUpdateStatus(Pair(id, false))
                    updateWeatherUnit(callback, bdUpdateStatus, scope)
                }
            })
        }
    }

    @WorkerThread
    private fun createUpdatingList(model: WeatherModel) {
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