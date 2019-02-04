package ru.kcoder.weatherhelper.data.resourses.string

interface WeatherStringSource {
    fun getDescriptionByCod(cod: Int): String
    fun getWindDescription(): String
    fun getHumidityDescription(): String
    fun getDayTitle(): String
}