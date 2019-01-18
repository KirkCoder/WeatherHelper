package ru.kcoder.weatherhelper.toolkit.utils

import java.util.*

object TimeUtils {

    fun isHourDifference(dt: Long, value: Long): Boolean {
        return dt - getCurrentUtcTime() < value
    }

    fun getDefaultUTCOffset(): Int {
        return TimeZone.getDefault().rawOffset
    }

    fun getCurrentUtcTime(): Long {
        return Calendar.getInstance().time.time - getDefaultUTCOffset()
    }
}