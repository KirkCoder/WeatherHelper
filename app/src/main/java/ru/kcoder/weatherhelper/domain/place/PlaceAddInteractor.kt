package ru.kcoder.weatherhelper.domain.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker

interface PlaceAddInteractor {
    fun getAddress(
        lat: Double, lon: Double,
        callback: (Pair<String?, String?>) -> Unit,
        errorCallback: (Int) -> Unit
    )

    fun savePlace(
        place: PlaceMarker,
        callback: (Long) -> Unit,
        errorCallback: (Int) -> Unit
    )

    fun getUTCoffset(
        lat: Double, lon: Double,
        callback: (Int) -> Unit,
        errorCallback: (Int) -> Unit)
}