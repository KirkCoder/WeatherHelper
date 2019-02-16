package ru.kcoder.weatherhelper.features.place

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.toolkit.farmework.BaseViewModel
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController

interface ContaractPlace {

    abstract class ViewModel(
        scopeController: ScopeController,
        errorSupervisor: ErrorSupervisor
    ) : BaseViewModel(scopeController, errorSupervisor) {

        abstract val markerLiveData: LiveData<PlaceMarker>
        abstract val addedPlaceIdLiveData: LiveData<WeatherHolder>
        abstract val fabVisibility: LiveData<Boolean>
        abstract val showDialog: LiveData<Boolean>
        abstract val progressLiveData: LiveData<Boolean>

        abstract fun updateViewModel(place: PlaceMarker)
        abstract fun savePlace()
        abstract fun updatePlaceName(name: String?)
    }

    interface Interactor : ScopeController {
        fun getAddress(
            lat: Double, lon: Double,
            callback: (Pair<String?, String?>) -> Unit,
            loadingStatus: (Boolean) -> Unit,
            onFail: () -> Unit
        )

        fun savePlace(
            place: PlaceMarker,
            callback: (WeatherHolder) -> Unit
        )

        fun getUTCoffset(
            lat: Double, lon: Double,
            callback: (Int) -> Unit
        )
    }
}