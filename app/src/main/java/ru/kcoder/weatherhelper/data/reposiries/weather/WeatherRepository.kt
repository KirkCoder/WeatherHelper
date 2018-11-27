package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHandler

interface WeatherRepository {
    fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherHandler
}