package ru.kcoder.weatherhelper.domain.place

interface PlaceAddInteractor {
    fun getAddress(
        lat: Double, lon: Double,
        callback: (Pair<String?, String?>) -> Unit,
        error: (Int) -> Unit
    )
}