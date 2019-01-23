package ru.kcoder.weatherhelper.domain.place

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor

class PlaceAddInteractorImpl(
    private val repository: PlaceRepository,
    settingsRepository: SettingsRepository
) : BaseInteractor(settingsRepository), PlaceAddInteractor {

    override fun getAddress(
        lat: Double, lon: Double,
        scope: CoroutineScope,
        callback: (Pair<String?, String?>) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loading(repository, scope, {
            getAddress(lat, lon)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    override fun savePlace(
        place: PlaceMarker,
        scope: CoroutineScope,
        callback: (WeatherHolder) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loading(repository, scope, {
            savePlace(place)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    override fun getUTCoffset(
        lat: Double, lon: Double,
        scope: CoroutineScope,
        callback: (Int) -> Unit,
        errorCallback: (Int) -> Unit) {
        loading(repository, scope,{
            getUTCoffset(lat, lon)
        }, {data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }
}