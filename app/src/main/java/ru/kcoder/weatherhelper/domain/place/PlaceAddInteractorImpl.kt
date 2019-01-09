package ru.kcoder.weatherhelper.domain.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor

class PlaceAddInteractorImpl(
    private val repository: PlaceRepository
) : BaseInteractor(), PlaceAddInteractor {

    override fun getAddress(
        lat: Double, lon: Double,
        callback: (Pair<String?, String?>) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loading(repository, {
            getAddress(lat, lon)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    override fun savePlace(
        place: PlaceMarker,
        callback: (Long) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loading(repository, {
            savePlace(place)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }

    override fun getUTCoffset(
        lat: Double, lon: Double,
        callback: (Int) -> Unit,
        errorCallback: (Int) -> Unit) {
        loading(repository, {
            getUTCoffset(lat, lon)
        }, {data, error ->
            data?.let { callback(it) }
            error?.let { errorCallback(it.msg.resourceString) }
        })
    }
}