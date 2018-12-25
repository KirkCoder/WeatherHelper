package ru.kcoder.weatherhelper.presentation.place

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractor

class ViewModelAddPlaceImpl(
    private val interactor: PlaceAddInteractor
) : ViewModelAddPlace() {

    private var markerData: PlaceMarker? = null
        set(value) {
            markerLiveData.value = value
        }

    override val markerLiveData: MutableLiveData<PlaceMarker> = MutableLiveData()
    override val addedPlaceIdLiveData: MutableLiveData<Long> = MutableLiveData()

    override fun updateViewModel(place: PlaceMarker) {
        markerData = place
        if (place.name == null) {
            interactor.getAddress(place.lat, place.lon, {
                markerData = place.apply {
                    name = it.first
                    address = it.second
                }
            }, this::errorCallback)
        }
    }

    override fun savePlace(place: PlaceMarker) {
        interactor.savePlace(place, {
            addedPlaceIdLiveData.value = it
        }, this::errorCallback)
    }
}