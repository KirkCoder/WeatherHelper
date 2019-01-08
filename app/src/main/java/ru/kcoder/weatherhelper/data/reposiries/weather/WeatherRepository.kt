package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderFuture
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderPresentation

interface WeatherRepository {
    fun getWeatherById(id: Long): WeatherHolderFuture

    fun getAllWeather(): WeatherModel

    fun getWeatherPresentationHolder(id: Long, update: Boolean = false): WeatherHolderPresentation
}