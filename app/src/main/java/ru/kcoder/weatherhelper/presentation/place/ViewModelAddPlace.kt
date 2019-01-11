package ru.kcoder.weatherhelper.presentation.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelAddPlace: BaseViewModel() {

    abstract val markerLiveData: LiveData<PlaceMarker>
    abstract val addedPlaceIdLiveData: LiveData<Long>
    abstract val fabVisibility: LiveData<Boolean>
    abstract val showDialog: LiveData<Boolean>
    abstract val progressLiveData: LiveData<Boolean>

    abstract fun updateViewModel(place: PlaceMarker)
    abstract fun savePlace()
    abstract fun updatePlaceName(name: String?)
}