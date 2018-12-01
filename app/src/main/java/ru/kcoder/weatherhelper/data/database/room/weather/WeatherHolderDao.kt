package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.*
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

@Dao
interface WeatherHolderDao: BaseDao<WeatherHolder> {

    @Query("SELECT * FROM weather_holder")
    fun getWeatherHolders(): List<WeatherHolder>

    @Query("SELECT * FROM weather_holder WHERE  id = :id LIMIT 1")
    fun getWeatherHolderById(id: Long): WeatherHolder?

    @Query("SELECT id FROM weather_holder WHERE lat = :lat AND lon = :lon LIMIT 1")
    fun getWeatherHolderId(lat: Double, lon: Double): Long?
}