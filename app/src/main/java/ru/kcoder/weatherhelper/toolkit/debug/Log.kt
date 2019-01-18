package ru.kcoder.weatherhelper.toolkit.debug

import android.util.Log

fun log(message: String, tag: String = "WEATHER_HELPER_DEBUG") {
    Log.d(tag, message)
}