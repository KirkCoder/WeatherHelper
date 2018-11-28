package ru.kcoder.weatherhelper.data.database.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.kcoder.weatherhelper.data.database.room.weather.WeatherHolderDao
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

@Database(
    version = 1,
    exportSchema = true,
    entities = [WeatherHolder::class]
)
abstract class WeatherHelperRoomDb: RoomDatabase() {

    abstract fun weather(): WeatherHolderDao

    companion object {
        const val DATABASE = "weather_helper"
    }
}