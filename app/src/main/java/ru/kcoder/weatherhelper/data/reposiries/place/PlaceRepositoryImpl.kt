package ru.kcoder.weatherhelper.data.reposiries.place

import android.location.Geocoder
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg

class PlaceRepositoryImpl(
    private val geocoder: Geocoder,
    private val weatherDbSource: WeatherDbSource
) : PlaceRepository {

    override fun getAddress(lat: Double, lon: Double): Pair<String?, String?> {
        val addresses = geocoder.getFromLocation(lat, lon, 1)
        if (addresses.isNotEmpty()) {
            var addr: String? = null
            if (addresses[0].adminArea != null) addr = addresses[0].adminArea
            if (addresses[0].countryName != null) {
                addr = addr?.let { "$it, ${addresses[0].countryName}" } ?: addresses[0].countryName
            }
            return Pair(addresses[0].locality, addr)
        }
        throw LocalException(LocalExceptionMsg.CANT_LOAD_ADDRESS)
    }

    override fun savePlace(place: PlaceMarker): Long {
        val weatherHolder = mapToWeatherHolder(place)
        if (weatherDbSource.getWeatherHolderId(place.lat, place.lon) != null) {
            throw LocalException(LocalExceptionMsg.PLACE_EXIST)
        }
        weatherDbSource.insertWeatherHolder(weatherHolder.apply {
            position = weatherDbSource.getLastPosition()?.let { it + 1 } ?: 0
        })
        return weatherDbSource.getWeatherHolderId(place.lat, place.lon)
            ?: throw LocalException(LocalExceptionMsg.UNEXPECTED_ERROR)
    }

    private fun mapToWeatherHolder(place: PlaceMarker): WeatherHolder {
        return WeatherHolder().apply {
            lat = place.lat
            lon = place.lon
            name = place.name ?: ""
        }
    }
}