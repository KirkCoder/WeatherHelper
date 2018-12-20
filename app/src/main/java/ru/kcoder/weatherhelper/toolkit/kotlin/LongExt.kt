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