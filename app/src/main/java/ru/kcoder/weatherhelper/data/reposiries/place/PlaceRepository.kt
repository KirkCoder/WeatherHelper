package ru.kcoder.weatherhelper.data.reposiries.place

import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface PlaceRepository {
    fun getAddress(lat: Double, lon: Double): Pair<String?, String?>
    fun savePlace(place: PlaceMarker): WeatherHolder
    fun getUTCoffset(lat: Double, lon: Double): Int
}