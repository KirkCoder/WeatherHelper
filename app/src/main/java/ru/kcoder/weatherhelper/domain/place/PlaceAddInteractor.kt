package ru.kcoder.weatherhelper.domain.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.presentation.common.BaseInteractor

interface PlaceAddInteractor: BaseInteractor {
    fun getAddress(
        lat: Double, lon: Double,
        callback: (Pair<String?, String?>) -> Unit,
        loadingStatus: (Boolean) -> Unit,
        onFail: () -> Unit
    )

    fun savePlace(
        place: PlaceMarker,
        callback: (WeatherHolder) -> Unit
    )

    fun getUTCoffset(
        lat: Double, lon: Double,
        callback: (Int) -> Unit)
}