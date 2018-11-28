package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

@Dao
interface WeatherHolderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(item: WeatherHolder)

    @Delete
    fun delete(item: WeatherHolder)

}