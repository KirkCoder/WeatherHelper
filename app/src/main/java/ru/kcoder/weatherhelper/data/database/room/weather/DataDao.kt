package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Data

@Dao
interface DataDao: BaseDao<Data> {

    @Query("SELECT * FROM list WHERE weatherHolderId = :id")
    fun getDataByWeatherHolderId(id: Long): List<Data>

    @Query("DELETE FROM list WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}