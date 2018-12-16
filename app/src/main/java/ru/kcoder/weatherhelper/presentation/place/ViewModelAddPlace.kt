package ru.kcoder.weatherhelper.presentation.place

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker

interface ViewModelAddPlace {

    val markerLiveData: MutableLiveData<PlaceMarker>
    val addressLiveData: MutableLiveData<PlaceMarker>

    fun updateViewModel(place: PlaceMarker, isNoAddress: Boolean = true)
}