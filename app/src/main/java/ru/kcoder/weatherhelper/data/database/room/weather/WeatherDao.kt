package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Weather

@Dao
interface WeatherDao: BaseDao<Weather> {

    @Query("SELECT * FROM weather WHERE weatherHolderId = :id")
    fun getWeatherByWeatherHolderId(id: Long): List<Weather>

    @Query("DELETE FROM weather WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}