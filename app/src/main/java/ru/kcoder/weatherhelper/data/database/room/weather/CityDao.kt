package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.City

@Dao
interface CityDao: BaseDao<City> {

    @Query("SELECT * FROM city WHERE weatherHolderId = :id")
    fun getCityByWeatherHolderId(id: Long): City?

    @Query("DELETE FROM city WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}