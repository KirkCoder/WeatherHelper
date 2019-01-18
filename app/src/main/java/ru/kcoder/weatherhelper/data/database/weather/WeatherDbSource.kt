package ru.kcoder.weatherhelper.data.database.weather

import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation

interface WeatherDbSource {
    fun getWeatherHolderId(lat: Double, lon: Double): Long?
    fun getLastPosition(): Int?
    fun insertWeatherHolder(holder: WeatherHolder)
    fun getSingleWeatherHolder(id: Long): WeatherHolder?
    fun insertWeatherPresentations(items: List<WeatherPresentation>)
    fun getWeather(id: Long): HolderWithPresentation?
    fun getAllWeather(): List<HolderWithPresentation>
    fun updateWeatherPresentations(id: Long, insertion: List<WeatherPresentation>)
}