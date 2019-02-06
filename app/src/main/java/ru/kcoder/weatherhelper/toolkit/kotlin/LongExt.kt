package ru.kcoder.weatherhelper.toolkit.kotlin

import java.text.SimpleDateFormat
import java.util.*

fun Long.tryFormatDate(): String {
    return this.getDate("EEE, HH:mm ")
}

fun Long.tryFormatTime(): String {
    return this.getDate("HH:mm")
}


fun Long.tryFormatDay(): String {
    return this.getDate("EEEE")
}

fun Long.tryFormatHour(): Int {
    return this.getDate("HH").toInt()
}

fun Long.getDate(format: String = "dd.MM.yyyy HH:mm"): String {
    return try {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        sdf.format(this)
    } catch (e: IllegalArgumentException) {
        ""
    }
}

/**
 * add milliseconds to Long date
 */
fun Long.addMilliseconds(): Long {
    return if (this.toString().toCharArray().size < 13) {
        this * 1000
    } else this
}