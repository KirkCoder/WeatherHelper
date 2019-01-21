package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "weather_holder")
class WeatherHolder {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var position: Int = 0
    var lat: Double = 0.0
    var lon: Double = 0.0
    var name: String = ""
    var timeUTCoffset: Int = 0

    @Ignore
    var hours = mutableListOf<WeatherPresentation>()

    @Ignore
    var timeNames = listOf<String>()

    @Ignore
    var days = mutableListOf<WeatherPresentation>()

    @Ignore
    var nights = mutableListOf<WeatherPresentation>()

    @Ignore
    var isUpdating = false
}