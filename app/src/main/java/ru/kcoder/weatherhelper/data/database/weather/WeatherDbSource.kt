package ru.kcoder.weatherhelper.data.database.weather

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition

interface WeatherDbSource {
    fun getWeatherHolderId(lat: Double, lon: Double): Long?
    fun getLastPosition(): Int?
    fun insertWeatherHolder(holder: WeatherHolder): WeatherHolder
    fun getSingleWeatherHolder(id: Long): WeatherHolder?
    fun getWeather(id: Long): HolderWithPresentation?
    fun getAllWeather(): List<HolderWithPresentation>
    fun updateWeatherPresentations(holder: WeatherHolder, insertion: List<WeatherPresentation>)
    fun deleteWeatherHolder(id: Long)
    fun changePositions(list: List<WeatherHolder>)
    fun getWeatherPositions(): LiveData<List<WeatherPosition>>

    fun getAllWeatherLd(): LiveData<List<HolderWithPresentation>>
    fun setLoadingStatus(id: Long)
    fun clearStatus(id: Long)
    fun clearAllStatus()
}