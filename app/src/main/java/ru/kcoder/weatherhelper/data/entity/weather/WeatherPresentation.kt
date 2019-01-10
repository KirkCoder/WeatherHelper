package ru.kcoder.weatherhelper.data.entity.weather

import androidx.annotation.IntDef
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kcoder.weatherhelper.ru.weatherhelper.R

@Entity(tableName = "weather")
data class WeatherPresentation(
    @setparam:StatusRange
    var status: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var holderId: Long = 0

    var dateAndDescription = ""
    var tempNow = "XX"
    var degreeThumbnail = "\u2103"
    var icoRes = R.drawable.ic_sun
    var humidity = ""
    var wind = ""
    var time = ""
    var day = ""
    var timeLong = 0L

    companion object {
        const val MAIN = 1
        const val HOURS = 2
        const val DAYS = 3
        const val NIGHTS = 4
    }
    @IntDef(MAIN, HOURS, DAYS, NIGHTS)
    annotation class StatusRange
}