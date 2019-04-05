package ru.kcoder.weatherhelper.data.entity.weather

import androidx.annotation.IntDef
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kcoder.weatherhelper.ru.weatherhelper.R

@Entity(tableName = "weather_presentation")
data class WeatherPresentation(
    @setparam:StatusRange
    var status: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var holderId: Long = 0

    var dateAndDescription = ""
    var tempNow = "XX"
    var tempNowWithThumbnail = ""
    var degreeThumbnail = "\u2103"
    var icoRes = R.drawable.ic_sun
    var icoResColored = R.drawable.ic_sun
    var humidity = ""
    var wind = ""
    var time = ""
    var day = ""
    var timeLong = 0L

    companion object {
        const val HOURS = 1
        const val DAYS = 2
        const val NIGHTS = 3
    }
    @IntDef(HOURS, DAYS, NIGHTS)
    annotation class StatusRange
}