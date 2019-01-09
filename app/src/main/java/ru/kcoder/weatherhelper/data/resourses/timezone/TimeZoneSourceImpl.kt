package ru.kcoder.weatherhelper.data.resourses.timezone

import ru.kcoder.weatherhelper.toolkit.utils.TimezoneMapper
import java.util.*

class TimeZoneSourceImpl: TimeZoneSource {

    override fun getTimeZoneOffset(lat: Double, lon: Double): Int {
        return TimeZone.getTimeZone(TimezoneMapper.latLngToTimezoneString(lat, lon)).rawOffset
    }
}