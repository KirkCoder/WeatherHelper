package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Clouds

@Dao
interface CloudsDao: BaseDao<Clouds> {

    @Query("SELECT * FROM clouds WHERE weatherHolderId = :id")
    fun getCloudsByWeatherHolderId(id: Long): Clouds?

    @Query("DELETE FROM clouds WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}