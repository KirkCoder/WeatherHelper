package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Wind

@Dao
interface WindDao: BaseDao<Wind> {

    @Query("SELECT * FROM wind WHERE weatherHolderId = :id")
    fun getWindByWeatherHolderId(id: Long): Wind?
}