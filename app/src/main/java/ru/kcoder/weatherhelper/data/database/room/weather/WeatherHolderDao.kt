package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

@Dao
interface WeatherHolderDao : BaseDao<WeatherHolder> {

    @Query("SELECT id FROM weather_holder WHERE lat = :lat AND lon = :lon")
    fun getWeatherHolderId(lat: Double, lon: Double): Long?

    @Query("SELECT position FROM weather_holder ORDER BY position DESC LIMIT 1")
    fun getLastPosition(): Int?

    @Query("SELECT * FROM weather_holder WHERE id = :id")
    fun getSingleWeatherHolder(id: Long): WeatherHolder?

    @Query("SELECT * FROM weather_holder WHERE id = :id LIMIT 1")
    fun getWeather(id: Long): HolderWithPresentation?

    @Query("SELECT * FROM weather_holder")
    fun getAllWeather(): List<HolderWithPresentation>
}