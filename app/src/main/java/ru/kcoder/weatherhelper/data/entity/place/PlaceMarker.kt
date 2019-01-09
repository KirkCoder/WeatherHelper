package ru.kcoder.weatherhelper.data.entity.place

data class PlaceMarker(
    val lat: Double,
    val lon: Double,
    var name: String? = null,
    var address: String? = null,
    var timeUTCoffset: Int = 0
)