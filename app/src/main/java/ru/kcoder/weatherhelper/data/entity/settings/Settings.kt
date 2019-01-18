package ru.kcoder.weatherhelper.data.entity.settings

class Settings {
    var degreeDifference: Double = 273.15
    var degreeThumbnail:String = "\u2103"
    var maxHourPoints = 5
    var startNight = 19
    var endNight = 6
    var updateTime = THREE_HOURS_IN_SECONDS

    companion object {
        private const val THREE_HOURS_IN_SECONDS = -10800000L
    }
}