package ru.kcoder.weatherhelper.presentation.place

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractor

class ViewModelAddPlaceImpl(
    private val interactor: PlaceAddInteractor
) : ViewModelAddPlace() {

    private var cashPlace: PlaceMarker? = null

    override val markerLiveData: MutableLiveData<PlaceMarker> = MutableLiveData()
    override val addedPlaceIdLiveData: MutableLiveData<Long> = MutableLiveData()
    override val fabVisibility: MutableLiveData<Boolean> = MutableLiveData()

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
            }, this::errorCallback)
        } else {
            setUTCoffset(place)
        }
    }

    private fun isPlaceValid(place: PlaceMarker): Boolean {
        val tmpPlace = cashPlace
        if (tmpPlace != null && tmpPlace.lat == place.lat && tmpPlace.lon == place.lon){
            return true
        }
        return false
    }

    private fun setUTCoffset(place: PlaceMarker) {
        interactor.getUTCoffset(place.lat, place.lon,{
            if (!isPlaceValid(place)) return@getUTCoffset
            markerLiveData.value = place.apply {
                timeUTCoffset = it
            }
            fabVisibility.value = true
        }, this::errorCallback)
    }

    override fun savePlace(place: PlaceMarker) {
        interactor.savePlace(place, {
            addedPlaceIdLiveData.value = it
        }, this::errorCallback)
    }

    override fun updatePlaceName(name: String?) {
        if (name != null){
            markerLiveData.value?.name = name
        } else {
            markerLiveData.value = null
        }
    }
}