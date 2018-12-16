package ru.kcoder.weatherhelper.presentation.place

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractor
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

class ViewModelAddPlaceImpl(
    private val interactor: PlaceAddInteractor
) : BaseViewModel(), ViewModelAddPlace {

    private var markerData: PlaceMarker? = null
        set(value) {
            markerLiveData.value = value
        }

    override val markerLiveData: MutableLiveData<PlaceMarker> = MutableLiveData()
    override val addressLiveData: MutableLiveData<PlaceMarker> = MutableLiveData()

    override fun updateViewModel(place: PlaceMarker, isNoAddress: Boolean) {
        markerData = place
        if (isNoAddress) {
            interactor.getAddress(place.lat, place.lon, {

            }, this::errorCallback)
        }
    }
}