package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Data

@Dao
interface DataDao: BaseDao<Data> {

    @Query("SELECT * FROM data WHERE weatherHolderId = :id")
    fun getDataByWeatherHolderId(id: Long): List<Data>
}