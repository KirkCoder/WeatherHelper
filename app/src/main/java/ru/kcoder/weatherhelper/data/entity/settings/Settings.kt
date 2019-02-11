package ru.kcoder.weatherhelper.data.entity.settings

class Settings {
    var degreeDifference: Double = DEFAULT_DEGREE_DIFFERENCE
    var degreeThumbnail:String = CELSIUS
    var maxHourPoints = DEFAULT_HOURS_POINTS
    var updateTime = THREE_HOURS_IN_SECONDS
    var startNight = 19
    var endNight = 6
    var serverTimeJump = 3

    companion object {
        private const val THREE_HOURS_IN_SECONDS = -10800000L
        const val CELSIUS = "\u2103"
        const val DEFAULT_DEGREE_DIFFERENCE = 273.15
        const val DEFAULT_HOURS_POINTS = 6
    }
}