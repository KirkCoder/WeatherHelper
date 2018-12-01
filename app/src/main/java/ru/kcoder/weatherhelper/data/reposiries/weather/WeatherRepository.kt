package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherRepository {
    fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherHolder

    fun getAllWeather(): List<WeatherHolder>
}