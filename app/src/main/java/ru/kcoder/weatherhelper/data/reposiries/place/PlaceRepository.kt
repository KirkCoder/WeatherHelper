package ru.kcoder.weatherhelper.data.reposiries.place

interface PlaceRepository {
    fun getAddress(lat: Double, lon: Double): Pair<String?, String?>
}