package ru.kcoder.weatherhelper.presentation.place

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelAddPlace: BaseViewModel() {

    abstract val markerLiveData: MutableLiveData<PlaceMarker>
    abstract val addedPlaceIdLiveData: MutableLiveData<Long>

    abstract fun updateViewModel(place: PlaceMarker)
    abstract fun savePlace(place: PlaceMarker)
}