package ru.kcoder.weatherhelper.domain.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.domain.common.AbstractInteractor

class PlaceAddInteractorImpl(
    private val repository: PlaceRepository,
    settingsRepository: SettingsRepository
) : AbstractInteractor(settingsRepository), PlaceAddInteractor {

    override fun getAddress(
        lat: Double, lon: Double,
        callback: (Pair<String?, String?>) -> Unit,
        loadingStatus: (Boolean) -> Unit,
        onFail: () -> Unit
    ) {
        loadingProgress({
            repository.getAddress(lat, lon)
        }, callback, loadingStatus, {
            onError(it)
            onFail.invoke()
        })
    }

    override fun savePlace(
        place: PlaceMarker,
        callback: (WeatherHolder) -> Unit
    ) {
        loading({
            repository.savePlace(place)
        }, callback)
    }

    override fun getUTCoffset(
        lat: Double, lon: Double,
        callback: (Int) -> Unit
    ) {
        loading({
            repository.getUTCoffset(lat, lon)
        }, callback)
    }
}