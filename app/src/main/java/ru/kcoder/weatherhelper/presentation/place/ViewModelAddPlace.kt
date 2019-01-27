package ru.kcoder.weatherhelper.presentation.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.presentation.common.BaseInteractor
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelAddPlace(
    baseInteractor: BaseInteractor
) : BaseViewModel(baseInteractor) {

    abstract val markerLiveData: LiveData<PlaceMarker>
    abstract val addedPlaceIdLiveData: LiveData<WeatherHolder>
    abstract val fabVisibility: LiveData<Boolean>
    abstract val showDialog: LiveData<Boolean>
    abstract val progressLiveData: LiveData<Boolean>

    abstract fun updateViewModel(place: PlaceMarker)
    abstract fun savePlace()
    abstract fun updatePlaceName(name: String?)
}