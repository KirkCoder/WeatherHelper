package ru.kcoder.weatherhelper.domain.place

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface PlaceAddInteractor {
    fun getAddress(
        lat: Double, lon: Double,
        scope: CoroutineScope,
        callback: (Pair<String?, String?>) -> Unit,
        errorCallback: (Int) -> Unit
    )

    fun savePlace(
        place: PlaceMarker,
        scope: CoroutineScope,
        callback: (WeatherHolder) -> Unit,
        errorCallback: (Int) -> Unit
    )

    fun getUTCoffset(
        lat: Double, lon: Double,
        scope: CoroutineScope,
        callback: (Int) -> Unit,
        errorCallback: (Int) -> Unit)
}