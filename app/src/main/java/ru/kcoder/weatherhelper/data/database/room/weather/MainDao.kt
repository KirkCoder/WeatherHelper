package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Main

@Dao
interface MainDao: BaseDao<Main> {

    @Query("SELECT * FROM main WHERE weatherHolderId = :id")
    fun getMainByWeatherHolderId(id: Long): Main?

    @Query("DELETE FROM main WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}