package ru.kcoder.weatherhelper.presentation.place

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractor
import ru.kcoder.weatherhelper.presentation.common.SingleLiveData

class ViewModelAddPlaceImpl(
    private val interactor: PlaceAddInteractor
) : ViewModelAddPlace() {

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
            progressLiveData.value = true
            interactor.getAddress(place.lat, place.lon, viewModelScope, {
                if (!isPlaceValid(place)) return@getAddress
                markerLiveData.value = place.apply {
                    name = it.first
                    address = it.second
                }
                setUTCoffset(place)
                progressLiveData.value = false
            }, {
                setUTCoffset(place)
                errorCallback(it)
                progressLiveData.value = false
            })
        } else {
            setUTCoffset(place)
        }
    }

    override fun savePlace() {
        cashPlace?.let {place ->
            if (place.name != null) {
                interactor.savePlace(place, viewModelScope, {
                    addedPlaceIdLiveData.value = it
                }, this::errorCallback)
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
        interactor.getUTCoffset(place.lat, place.lon, viewModelScope, {
            if (!isPlaceValid(place)) return@getUTCoffset
            markerLiveData.value = place.apply {
                timeUTCoffset = it
            }
            fabVisibility.value = true
        }, this::errorCallback)
    }
}