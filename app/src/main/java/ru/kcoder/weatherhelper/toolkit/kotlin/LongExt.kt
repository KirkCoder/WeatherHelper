package ru.kcoder.weatherhelper.toolkit.kotlin

import java.text.SimpleDateFormat
import java.util.*

fun Long.tryFormatDate(): String {
    return this.getDate("EEE, HH:mm ")
}

fun Long.tryFormatTime(): String {
    return this.getDate("hhaa")
}


fun Long.tryFormatDay(): String {
    return this.getDate("EEEEEEE")
}

fun Long.tryFormatHour(): Int {
    return this.getDate("HH").toInt()
}

fun Long.getDate(format: String = "dd.MM.yyyy HH:mm"): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this.addMilliseconds())
    } catch (e: IllegalArgumentException) {
        ""
    }
}

/**
 * add milliseconds to Long date
 */
private fun Long.addMilliseconds(): Long {
    return if (this.toString().toCharArray().size < 13) {
        this * 1000
    } else this
}