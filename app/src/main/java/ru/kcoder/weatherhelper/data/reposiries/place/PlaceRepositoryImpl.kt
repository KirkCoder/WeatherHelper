package ru.kcoder.weatherhelper.data.reposiries.place

import android.location.Geocoder
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg

class PlaceRepositoryImpl(
    private val geocoder: Geocoder
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
}