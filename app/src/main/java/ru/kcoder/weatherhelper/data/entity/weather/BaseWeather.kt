package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.PrimaryKey

abstract class BaseWeather {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var lat: Double? = null
    var lon: Double? = null
}