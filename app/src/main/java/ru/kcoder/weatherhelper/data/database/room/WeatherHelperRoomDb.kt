package ru.kcoder.weatherhelper.data.database.room

import android.arch.persistence.room.RoomDatabase

abstract class WeatherHelperRoomDb: RoomDatabase() {
    companion object {
        const val DATABASE = "weather_helper"
    }
}