package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.PrimaryKey

abstract class BaseWeather {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var lat: Double? = null
    var lon: Double? = null
}