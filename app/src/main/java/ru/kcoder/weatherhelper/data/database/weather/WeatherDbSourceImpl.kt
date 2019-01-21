package ru.kcoder.weatherhelper.data.database.weather

import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation

class WeatherDbSourceImpl(private val database: WeatherHelperRoomDb) : WeatherDbSource {

    override fun getWeatherHolderId(lat: Double, lon: Double): Long? {
        return database.weatherHolder().getWeatherHolderId(lat, lon)
    }

    override fun getLastPosition(): Int? {
        return database.weatherHolder().getLastPosition()
    }

    override fun insertWeatherHolder(holder: WeatherHolder): WeatherHolder {
        return database.weatherHolder().insertWeatherHolder(holder)
    }

    override fun getSingleWeatherHolder(id: Long): WeatherHolder? {
        return database.weatherHolder().getSingleWeatherHolder(id)
    }

    override fun updateWeatherPresentations(id: Long, insertion: List<WeatherPresentation>) {
        database.weatherPresentation().updateWeatherPresentations(id, insertion)
    }

    override fun insertWeatherPresentations(items: List<WeatherPresentation>) {
        database.weatherPresentation().insertOrReplace(items)
    }

    override fun getWeather(id: Long): HolderWithPresentation? {
        return database.weatherHolder().getWeather(id)
    }

    override fun getAllWeather(): List<HolderWithPresentation> {
        return database.weatherHolder().getAllWeather()
    }

    override fun deleteWeatherHolder(id: Long) {
        database.weatherHolder().delete(id)
    }
}