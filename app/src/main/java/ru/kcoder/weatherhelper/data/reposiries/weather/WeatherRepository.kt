package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentationHolder

interface WeatherRepository {
    fun getWeatherById(id: Long): WeatherHolder

    fun getAllWeather(): WeatherModel

    fun getWeatherPresentationHolder(id: Long, update: Boolean = false): WeatherPresentationHolder
}