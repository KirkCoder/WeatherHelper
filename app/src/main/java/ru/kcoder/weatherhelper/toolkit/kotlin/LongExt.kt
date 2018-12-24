package ru.kcoder.weatherhelper.toolkit.kotlin

import java.text.SimpleDateFormat
import java.util.*

fun Long?.tryFormatDate(default: String = ""): String {
    return try {
        SimpleDateFormat("MMM, HH:mm aa, ", Locale.getDefault()).format(this)
    } catch (e: IllegalArgumentException) {
        default
    }
}

fun Long?.tryFormatTime(default: String = ""): String {
    return try {
        SimpleDateFormat("hhaa", Locale.getDefault()).format(this)
    } catch (e: IllegalArgumentException) {
        default
    }
}


fun Long?.tryFormatDay(default: String = ""): String {
    return try {
        SimpleDateFormat("EEEEEEE", Locale.getDefault()).format(this)
    } catch (e: IllegalArgumentException) {
        default
    }
}

fun Long?.getHour(default: Int = 0): Int {
    return try {
        SimpleDateFormat("HH", Locale.getDefault()).format(this).toInt()
    } catch (e: IllegalArgumentException) {
        default
    }
}