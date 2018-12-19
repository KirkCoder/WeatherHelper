package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.list.WeatherModel

interface WeatherRepository {
    fun getWeatherById(id: Long): WeatherHolder

    fun getAllWeather(): WeatherModel
}