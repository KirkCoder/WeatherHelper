package ru.kcoder.weatherhelper.presentation.place

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelAddPlace: BaseViewModel() {

    abstract val markerLiveData: MutableLiveData<PlaceMarker>
    abstract val addedPlaceIdLiveData: MutableLiveData<Long>
    abstract val fabVisibility: MutableLiveData<Boolean>

    abstract fun updateViewModel(place: PlaceMarker)
    abstract fun savePlace(place: PlaceMarker)
    abstract fun updatePlaceName(name: String?)
}