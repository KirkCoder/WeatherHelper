package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.*
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

@Dao
abstract class WeatherHolderDao {

    @Query("SELECT id FROM weather_holder WHERE lat = :lat AND lon = :lon")
    abstract fun getWeatherHolderId(lat: Double, lon: Double): Long?

    @Query("SELECT position FROM weather_holder ORDER BY position DESC LIMIT 1")
    abstract fun getLastPosition(): Int?

    @Query("SELECT * FROM weather_holder WHERE id = :id")
    abstract fun getSingleWeatherHolder(id: Long): WeatherHolder?

    @Transaction @Query("SELECT * FROM weather_holder WHERE id = :id LIMIT 1")
    abstract fun getWeather(id: Long): HolderWithPresentation?

    @Transaction @Query("SELECT * FROM weather_holder")
    abstract fun getAllWeather(): List<HolderWithPresentation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOrReplace(holder: WeatherHolder)

    @Query("DELETE FROM weather_holder WHERE id =:id")
    abstract fun deleteWeatherHolder(id: Long)

    @Query("DELETE FROM weather_presentation WHERE holderId = :id")
    abstract fun deleteWeatherPresentations(id: Long)

    @Transaction
    open fun delete(id: Long){
        deleteWeatherHolder(id)
        deleteWeatherPresentations(id)
    }
}