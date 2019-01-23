package ru.kcoder.weatherhelper.domain.weather.list

import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils

class WeatherListInteractorImpl(
    private val repository: WeatherRepository,
    settingsRepository: SettingsRepository
) : BaseInteractor(settingsRepository), WeatherListInteractor {

    @Volatile
    private var updatingId: Long? = null

    override fun getAllWeather(
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit,
        errorCallback: ((Int) -> Unit)
    ) {
        runWithSettings(scope, errorCallback) { settings ->
            loading(repository, scope, {
                getAllWeather(settings).also {
                    findNotUpdatedItem(settings, it)
                }
            }, { data, error ->
                data?.let {
                    callback(it)
                    updateWeatherUnit(scope, callback, errorCallback, bdUpdateStatus)
                }
                error?.let {
                    errorCallback.invoke(it.msg.resourceString)
                }
            })
        }
    }

    override fun forceUpdate(
        id: Long,
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit)
    ) {
        runWithSettings(scope, errorCallback) { settings ->
            loading(repository, scope, {
                getWeatherById(settings, id)
            }, { data, error ->
                data?.let { callback(it) }
                error?.let { errorCallback(it.msg.resourceString) }
            })
        }
    }

    private fun updateWeatherUnit(
        scope: CoroutineScope,
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit),
        bdUpdateStatus: (Pair<Long, Boolean>) -> Unit
    ) {
        runWithSettings(scope, errorCallback) { settings ->
            val id = updatingId
            if (id != null) {
                bdUpdateStatus(Pair(id, true))
                loading(repository, scope, {
                    getWeatherById(settings, id)
                }, { data, error ->
                    data?.let {
                        updatingId = null
                        callback(it)
                    }
                    error?.let {
                        log(it.message ?: it.toString())
                        bdUpdateStatus(Pair(id, false))
                        updateWeatherUnit(scope, callback, errorCallback, bdUpdateStatus)
                    }
                })
            }
        }
    }

    override fun delete(
        id: Long, scope: CoroutineScope
    ) {
        uploading(repository, scope) {
            delete(id)
        }
    }

    override fun changedData(
        list: List<WeatherHolder>,
        scope: CoroutineScope
    ) {
        uploading(repository, scope) {
            changedData(list)
        }
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