package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHandler
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig

class WeatherRepositoryImpl(
    val network: WeatherNetworkSource,
    val database: WeatherDbSource
) : WeatherRepository {

    override fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherHandler {
        network.getWeatherByCoordinate(lat, lon, BuildConfig.API_KEY)
    }

}