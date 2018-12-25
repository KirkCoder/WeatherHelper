package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Wind

@Dao
interface WindDao: BaseDao<Wind> {

    @Query("SELECT * FROM wind WHERE weatherHolderId = :id")
    fun getWindByWeatherHolderId(id: Long): Wind?

    @Query("DELETE FROM wind WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}