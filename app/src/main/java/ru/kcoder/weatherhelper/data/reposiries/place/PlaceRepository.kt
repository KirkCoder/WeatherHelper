package ru.kcoder.weatherhelper.data.reposiries.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker

interface PlaceRepository {
    fun getAddress(lat: Double, lon: Double): Pair<String?, String?>
    fun savePlace(place: PlaceMarker): Long
    fun getUTCoffset(lat: Double, lon: Double): Int
}