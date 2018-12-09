package ru.kcoder.weatherhelper.toolkit.utils

import java.util.*

object TimeUtils {

    private const val THREE_HOURS_IN_SECONDS = -10800L

    fun getCurrentTime(): Long{
        return Calendar.getInstance().time.time /1000
    }

    fun isThreeHourDifference(dt: Long): Boolean {
        return dt - getCurrentTime() < THREE_HOURS_IN_SECONDS
    }

}