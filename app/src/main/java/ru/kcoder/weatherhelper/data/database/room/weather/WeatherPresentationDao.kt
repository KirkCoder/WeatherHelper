package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.*
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation

@Dao
abstract class WeatherPresentationDao {

    @Query("DELETE FROM weatherUpdate WHERE holderId = :id")
    abstract fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(list: List<WeatherPresentation>)

    @Transaction
    open fun updateWeatherPresentations(id: Long, insertion: List<WeatherPresentation>){
        delete(id)
        insertOrReplace(insertion)
    }
}