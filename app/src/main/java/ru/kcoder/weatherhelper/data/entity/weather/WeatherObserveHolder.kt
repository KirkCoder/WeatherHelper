package ru.kcoder.weatherhelper.data.entity.weather

class WeatherObserveHolder {
    var id: Long = 0
    var position: Int = 0
    var lat: Double = 0.0
    var lon: Double = 0.0
    var name: String = ""
    var hours = listOf<WeatherObserve>()
    var days = listOf<WeatherObserve>()
}