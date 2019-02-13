package ru.kcoder.weatherhelper.features.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.toolkit.farmework.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

class InteractorPlace(
    private val repository: PlaceRepository,
    settingsRepository: SettingsRepository,
    scopeHandler: ScopeHandler,
    errorSupervisor: ErrorSupervisor
) : BaseInteractor(settingsRepository, scopeHandler, errorSupervisor),
    ContaractPlace.Interactor {

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