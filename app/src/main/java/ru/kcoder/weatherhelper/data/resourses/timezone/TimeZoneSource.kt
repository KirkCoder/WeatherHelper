package ru.kcoder.weatherhelper.data.resourses.timezone

interface TimeZoneSource {
    fun getTimeZoneOffset(lat: Double, lon: Double): Int
}