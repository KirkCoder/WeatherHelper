package ru.kcoder.weatherhelper.data.reposiries.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

interface WeatherRepository {
    fun getWeatherById(id: Long): WeatherHolder

    fun getAllWeather(): LiveData<WeatherModel>

    fun getWeather(id: Long, update: Boolean = false): WeatherHolder
}