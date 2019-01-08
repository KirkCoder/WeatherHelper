package ru.kcoder.weatherhelper.data.database.weather

import ru.kcoder.weatherhelper.data.entity.weather.*

interface WeatherDbSource {
    fun getWeatherHolders(): List<WeatherHolderFuture>
    fun getWeatherHolderId(lat: Double, lon: Double): Long?
    fun getWeatherHolderPosition(id: Long): Int
    fun getLastPosition(): Int?
    fun dropOldWeatherHolderChildren(id: Long)
    fun updateWeatherHolder(weatherHolder: WeatherHolderFuture)
    fun insertWeatherHolder(weatherHolder: WeatherHolderFuture)
    fun insertWeatherHolderChildrens(weatherHolder: WeatherHolderFuture)
    fun getSingleWeatherHolder(id: Long): WeatherHolderFuture?
    fun getWeatherHolder(id: Long): WeatherHolderFuture?
}