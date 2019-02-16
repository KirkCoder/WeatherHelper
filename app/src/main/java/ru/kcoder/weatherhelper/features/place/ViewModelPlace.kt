package ru.kcoder.weatherhelper.features.place

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import ru.kcoder.weatherhelper.toolkit.farmework.components.SingleLiveData

class ViewModelPlace(
    private val interactor: ContaractPlace.Interactor,
    errorSupervisor: ErrorSupervisor
) : ContaractPlace.ViewModel(interactor, errorSupervisor) {

    private var cashPlace: PlaceMarker? = null

    override val markerLiveData = MutableLiveData<PlaceMarker>()
    override val addedPlaceIdLiveData = MutableLiveData<WeatherHolder>()
    override val fabVisibility = MutableLiveData<Boolean>()
    override val progressLiveData = MutableLiveData<Boolean>()
    override val showDialog = SingleLiveData<Boolean>()

    override fun updateViewModel(place: PlaceMarker) {
        cashPlace = place
        fabVisibility.value = false
        markerLiveData.value = place
        if (place.name == null) {
            interactor.getAddress(place.lat, place.lon, {
                if (!isPlaceValid(place)) return@getAddress
                markerLiveData.value = place.apply {
                    name = it.first
                    address = it.second
                }
                setUTCoffset(place)
            }, {
                progressLiveData.value = it
            }, { setUTCoffset(place) })
        } else {
            setUTCoffset(place)
        }
    }

    override fun savePlace() {
        cashPlace?.let { place ->
            if (place.name != null) {
                interactor.savePlace(place) {
                    addedPlaceIdLiveData.value = it
                }
            } else {
                showDialog.value = true
            }
        }
    }

    override fun updatePlaceName(name: String?) {
        if (name != null) {
            markerLiveData.value?.name = name
            cashPlace?.name = name
            savePlace()
        }
    }

    private fun isPlaceValid(place: PlaceMarker): Boolean {
        val tmpPlace = cashPlace
        if (tmpPlace != null && tmpPlace.lat == place.lat && tmpPlace.lon == place.lon) {
            return true
        }
        return false
    }

    private fun setUTCoffset(place: PlaceMarker) {
        interactor.getUTCoffset(place.lat, place.lon) {
            if (!isPlaceValid(place)) return@getUTCoffset
            markerLiveData.value = place.apply {
                timeUTCoffset = it
            }
            fabVisibility.value = true
        }
    }
}