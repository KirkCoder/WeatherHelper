package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation

@Dao
interface WeatherPresentationDao: BaseDao<WeatherPresentation> {

    @Query("DELETE FROM weather WHERE holderId = :id")
    fun delete(id: Long)
}