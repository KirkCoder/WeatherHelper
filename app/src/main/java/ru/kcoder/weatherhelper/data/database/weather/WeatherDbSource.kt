package ru.kcoder.weatherhelper.data.database.weather

import ru.kcoder.weatherhelper.data.entity.weather.*

interface WeatherDbSource {
    fun getWeatherHolders(): List<WeatherHolder>
    fun getWeatherHolderId(lat: Double, lon: Double): Long?
    fun getWeatherHolderPosition(id: Long): Int
    fun getLastPosition(): Int?
    fun dropOldWeatherHolderChildren(id: Long)
    fun updateWeatherHolder(weatherHolder: WeatherHolder)
    fun insertWeatherHolder(weatherHolder: WeatherHolder)
    fun insertWeatherHolderChildrens(weatherHolder: WeatherHolder)
    fun getSingleWeatherHolder(id: Long): WeatherHolder?
}