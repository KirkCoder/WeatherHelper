package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Coord

@Dao
interface CoordDao: BaseDao<Coord> {

    @Query("SELECT * FROM coord WHERE weatherHolderId = :id")
    fun getCoordByWeatherHolderId(id: Long): Coord?

    @Query("DELETE FROM coord WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}