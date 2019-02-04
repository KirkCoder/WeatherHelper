package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherRepository {
    fun updateWeatherById(settings: Settings, id: Long): WeatherModel
    fun getAllWeather(settings: Settings, updatedId: Long = WeatherModel.BROADCAST): WeatherModel
    fun getWeather(settings: Settings, id: Long, update: Boolean = false): WeatherHolder
    fun delete(id: Long)
    fun changedData(list: List<WeatherHolder>)
    fun getDayTitle(): String
}