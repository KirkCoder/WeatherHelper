package ru.kcoder.weatherhelper.data.resourses.imageres

interface ImageResSource {
    fun getImageIdByCod(cod: Int?, isDay: Boolean): Int
}