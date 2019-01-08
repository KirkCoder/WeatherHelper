package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.*
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderFuture

@Dao
interface WeatherHolderDao: BaseDao<WeatherHolderFuture> {

    @Query("SELECT * FROM weather_holder")
    fun getWeatherHolders(): List<WeatherHolderFuture>

    @Query("SELECT * FROM weather_holder WHERE  id = :id LIMIT 1")
    fun getWeatherHolderById(id: Long): WeatherHolderFuture?

    @Query("SELECT id FROM weather_holder WHERE lat = :lat AND lon = :lon LIMIT 1")
    fun getWeatherHolderId(lat: Double, lon: Double): Long?

    @Query("SELECT position FROM weather_holder ORDER BY position DESC LIMIT 1")
    fun getLastPosition(): Int?

    @Query("SELECT position FROM weather_holder WHERE weather_holder.id = :id LIMIT 1")
    fun getWeatherHolderPosition(id: Long): Int
}