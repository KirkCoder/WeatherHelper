package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherRepository {
    fun getWeatherById(id: Long): WeatherHolder
    fun getAllWeather(): WeatherModel
    fun getWeather(id: Long, update: Boolean = false): WeatherHolder
    fun getMockedWeather(): WeatherHolder
    fun delete(id: Long): Boolean
}