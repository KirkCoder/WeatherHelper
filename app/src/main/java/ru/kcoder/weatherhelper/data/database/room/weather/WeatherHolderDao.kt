package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg

@Dao
abstract class WeatherHolderDao {

    @Query("SELECT id FROM weather_holder WHERE lat = :lat AND lon = :lon")
    abstract fun getWeatherHolderId(lat: Double, lon: Double): Long?

    @Query("SELECT position FROM weather_holder ORDER BY position DESC LIMIT 1")
    abstract fun getLastPosition(): Int?

    @Query("SELECT * FROM weather_holder WHERE id = :id")
    abstract fun getSingleWeatherHolder(id: Long): WeatherHolder?

    @Transaction
    @Query("SELECT * FROM weather_holder WHERE id = :id LIMIT 1")
    abstract fun getWeather(id: Long): HolderWithPresentation?

    @Query("SELECT * FROM weather_holder WHERE id = :id LIMIT 1")
    abstract fun getWeatherHolder(id: Long): WeatherHolder?

    @Transaction
    @Query("SELECT * FROM weather_holder")
    abstract fun getAllWeather(): List<HolderWithPresentation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertHolder(holder: WeatherHolder)

    @Query("SELECT last_insert_rowid() FROM weather_holder")
    abstract fun getLustId(): Long?

    @Query("DELETE FROM weather_holder WHERE id =:id")
    abstract fun deleteWeatherHolder(id: Long)

    @Query("DELETE FROM weather_presentation WHERE holderId = :id")
    abstract fun deleteWeatherPresentations(id: Long)

    @Query("UPDATE weather_holder SET position = :position WHERE id = :id ")
    abstract fun changePosition(id: Long, position: Int)

    @Transaction
    open fun insertWeatherHolder(holder: WeatherHolder): WeatherHolder {
        holder.position = getLastPosition()?.let { it + 1 } ?: 0
        insertHolder(holder)
        return getLustId()?.let {
            getWeatherHolder(it) ?: throw LocalException(LocalExceptionMsg.UNEXPECTED_ERROR)
        } ?: throw LocalException(LocalExceptionMsg.UNEXPECTED_ERROR)
    }

    @Transaction
    open fun delete(id: Long) {
        deleteWeatherHolder(id)
        deleteWeatherPresentations(id)
    }

    @Transaction
    open fun changePositions(list: List<WeatherHolder>) {
        for ((i, holder) in list.withIndex()) {
            changePosition(holder.id, i)
        }
    }

    @Query("SELECT id, position, name FROM weather_holder ORDER BY position")
    abstract fun getWeatherPositions(): LiveData<List<WeatherPosition>>

    @Transaction
    @Query("SELECT * FROM weather_holder")
    abstract fun getAllWeatherLd(): LiveData<List<HolderWithPresentation>>

    @Query("UPDATE weather_holder SET isUpdating = 0")
    abstract fun clearStatus()

    @Query("UPDATE weather_holder SET isUpdating = :status WHERE id = :id")
    abstract fun setStatus(id: Long, status: Int)

    @Query("DELETE FROM weather_presentation WHERE holderId = :id")
    abstract fun deletePresentations(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPresentations(list: List<WeatherPresentation>)

    @Transaction
    open fun updateWeatherPresentations(holder: WeatherHolder, insertion: List<WeatherPresentation>){
        insertHolder(holder)
        deletePresentations(holder.id)
        insertPresentations(insertion)
    }
}