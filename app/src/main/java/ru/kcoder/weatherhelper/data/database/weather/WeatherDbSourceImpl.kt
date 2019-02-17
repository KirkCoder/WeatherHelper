package ru.kcoder.weatherhelper.data.database.weather

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.entity.weather.HolderWithPresentation
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition

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

    override fun updateWeatherPresentations(holder: WeatherHolder, insertion: List<WeatherPresentation>) {
        database.weatherHolder().updateWeatherPresentations(holder, insertion)
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

    override fun changePositions(list: List<WeatherHolder>) {
        database.weatherHolder().changePositions(list)
    }

    override fun getWeatherPositions(): LiveData<List<WeatherPosition>> {
        return database.weatherHolder().getWeatherPositions()
    }


    override fun getAllWeatherLd(): LiveData<List<HolderWithPresentation>> {
        return database.weatherHolder().getAllWeatherLd()
    }

    override fun setLoadingStatus(id: Long) {
        database.weatherHolder().setStatus(id, 1)
    }

    override fun clearStatus(id: Long) {
        database.weatherHolder().setStatus(id, 0)
    }

    override fun clearAllStatus() {
        database.weatherHolder().clearStatus()
    }
}