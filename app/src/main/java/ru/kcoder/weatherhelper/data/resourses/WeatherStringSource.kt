package ru.kcoder.weatherhelper.data.resourses

interface WeatherStringSource {
    fun getDescriptionByCod(cod: Int): String
    fun getWindDescription(): String
    fun getHumidityDescription(): String
}