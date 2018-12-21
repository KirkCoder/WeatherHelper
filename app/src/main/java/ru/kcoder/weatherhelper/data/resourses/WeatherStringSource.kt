package ru.kcoder.weatherhelper.data.resourses

interface WeatherStringSource {
    fun getDescriptionByCod(cod: Int): String
}