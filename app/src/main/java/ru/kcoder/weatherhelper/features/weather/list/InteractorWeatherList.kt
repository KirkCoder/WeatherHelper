package ru.kcoder.weatherhelper.features.weather.list

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.toolkit.farmework.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils

class InteractorWeatherList(
    private val repository: WeatherRepository,
    scopeHandler: ScopeHandler,
    settingsRepository: SettingsRepository,
    errorSupervisor: ErrorSupervisor
) : BaseInteractor(settingsRepository, scopeHandler, errorSupervisor),
    ContractWeatherList.Interactor {

    @Volatile
    private var updatingId: Long? = null
    private val failBgUpdate = mutableListOf<Long>()

    private val allWeatherLiveData = MediatorLiveData<List<WeatherHolder>>()

    override fun getAllWeather(): LiveData<List<WeatherHolder>> {
        runWithSettings { settings ->
            loading({ repository.clearAllStatus() }, {
                allWeatherLiveData.addSource(
                    repository.getAllWeather(settings, scopeHandler.scope)
                ) { list -> list?.let {
                    scopeHandler.scope.launch (Dispatchers.IO){
                        if (updatingId == null) findNotUpdatedItem(settings, it)
                    }
                    allWeatherLiveData.value = it
                } }
            })
        }
        return allWeatherLiveData
    }

    override fun forceUpdate(id: Long) {
        uploading(
            scope = scopeHandler.scope,
            upload = { repository.setLoadingStatus(id) },
            success = {
                if (it) {
                    runWithSettings { settings ->
                        loading({
                            repository.updateWeatherById(settings, id)
                        }, errorCallback = {
                            clearUpdateStatus(id)
                        })
                    }
                } else {
                    clearUpdateStatus(id)
                }
            }
        )
    }

    private fun clearUpdateStatus(id: Long) {
        if (id == updatingId) {
            updatingId = null
            failBgUpdate.add(id)
        }
        uploading({ repository.clearStatus(id) })
    }

    override fun delete(id: Long) {
        uploading({ repository.delete(id) })
    }

    override fun changedData(list: List<WeatherHolder>) {
        uploading({ repository.changedData(list) })
    }

    @WorkerThread
    private fun findNotUpdatedItem(settings: Settings, list: List<WeatherHolder>) {
        for (holder in list) {
            val data = holder.hours
            val updateTime = settings.updateTime
            if (!data.isNullOrEmpty()
                && TimeUtils.isHourDifference(data[0].timeLong - holder.timeUTCoffset, updateTime)
                && !failBgUpdate.contains(holder.id)
            ) {
                updatingId = holder.id
                forceUpdate(holder.id)
            }
        }
    }

    override fun clearStatus() {
        uploading({ repository.clearAllStatus() })
    }
}