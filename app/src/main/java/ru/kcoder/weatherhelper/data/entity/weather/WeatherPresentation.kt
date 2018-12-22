package ru.kcoder.weatherhelper.data.entity.weather

class WeatherPresentation {
    var dateAndDescription = ""
    var tempNow = "XX"
    var degreeThumbnail = "\u2103"
    var IcoConst = SAN
    var humidity = ""
    var wind = ""
    var time = ""
    var day = ""


    companion object {
        const val SAN = 1
    }
}