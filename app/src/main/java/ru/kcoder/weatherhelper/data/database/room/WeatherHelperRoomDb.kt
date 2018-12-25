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
        Data::class,
        Clouds::class,
        Coord::class,
        Main::class,
        Rain::class,
        Sys::class,
        Weather::class,
        Wind::class,
        City::class]
)
abstract class WeatherHelperRoomDb : RoomDatabase() {

    abstract fun weatherHolder(): WeatherHolderDao
    abstract fun city(): CityDao
    abstract fun clouds(): CloudsDao
    abstract fun coord(): CoordDao
    abstract fun data(): DataDao
    abstract fun main(): MainDao
    abstract fun rain(): RainDao
    abstract fun sys(): SysDao
    abstract fun weather(): WeatherDao
    abstract fun wind(): WindDao


    companion object {
        const val DATABASE = "weather_helper"
    }
}