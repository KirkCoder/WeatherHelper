package ru.kcoder.weatherhelper.data.reposiries.weather

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition

interface WeatherRepository {
    fun updateWeatherById(settings: Settings, id: Long): WeatherModel
    fun getAllWeather(settings: Settings, updatedId: Long = WeatherModel.BROADCAST): WeatherModel
    fun getWeather(settings: Settings, id: Long, update: Boolean = false): WeatherHolder
    fun delete(id: Long)
    fun changedData(list: List<WeatherHolder>)
    fun getDayTitle(): String
    fun getWeatherPositions(): LiveData<List<WeatherPosition>>
}