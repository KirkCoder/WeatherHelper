package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Sys

@Dao
interface SysDao: BaseDao<Sys> {

    @Query("SELECT * FROM sys WHERE weatherHolderId = :id")
    fun getSysByWeatherHolderId(id: Long): Sys?

    @Query("DELETE FROM sys WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}