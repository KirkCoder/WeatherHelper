package ru.kcoder.weatherhelper.data.entity.place

data class PlaceMarker(
    val lat: Double,
    val lon: Double,
    val name: String? = null,
    val address: String? = null
)