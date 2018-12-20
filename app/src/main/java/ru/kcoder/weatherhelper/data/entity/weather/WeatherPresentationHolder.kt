package ru.kcoder.weatherhelper.data.entity.weather

class WeatherPresentationHolder {
    var id: Long = 0
    var position: Int = 0
    var lat: Double = 0.0
    var lon: Double = 0.0
    var name: String = ""
    var hours = mutableListOf<WeatherPresentation>()
    var days = mutableListOf<WeatherPresentation>()
}