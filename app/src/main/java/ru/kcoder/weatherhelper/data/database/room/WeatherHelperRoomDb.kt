package ru.kcoder.weatherhelper.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kcoder.weatherhelper.data.database.room.weather.*
import ru.kcoder.weatherhelper.data.entity.weather.*

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        WeatherHolder::class,
        WeatherPresentation::class]
)
abstract class WeatherHelperRoomDb : RoomDatabase() {

    abstract fun weatherHolder(): WeatherHolderDao
    abstract fun weatherPresentation(): WeatherPresentationDao

    companion object {
        const val DATABASE = "weather_helper"
    }
}