package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.domain.weather.list.WeatherModel
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.WrongApiResponse

class WeatherRepositoryImpl(
    val network: WeatherNetworkSource,
    val database: WeatherDbSource
) : WeatherRepository {

    override fun getWeatherByCoordinate(lat: Double, lon: Double): WeatherHolder {
        val weatherHolder = network.getWeatherByCoordinate(lat, lon, BuildConfig.API_KEY)
            .execute().body()
        if (weatherHolder?.cod != null && weatherHolder.cod.equals("200")) {
            weatherHolder.lat = lat
            weatherHolder.lon = lon
            var whId: Long? = null
            whId = database.getWeatherHolderId(lat, lon)
            if (whId != null) {
                database.dropOldWeatherHolderChildren(whId)
                database.updateWeatherHolder(weatherHolder.apply {
                    id = whId as Long
                    position = database.getWeatherHolderPosition(id)
                })
            } else {
                database.insertWeatherHolder(weatherHolder.apply {
                    position = database.getLastPosition()?.let { it + 1 } ?: 0
                })
                whId = database.getWeatherHolderId(lat, lon)
            }
            if (whId != null) {
                weatherHolder.id = whId
                weatherHolder.city?.weatherHolderId = whId
                weatherHolder.data?.forEach {
                    it.weatherHolderId = whId
                    it.weather?.forEach { weather -> weather.weatherHolderId = whId }
                    it.clouds?.weatherHolderId = whId
                    it.sys?.weatherHolderId = whId
                    it.wind?.weatherHolderId = whId
                    it.main?.weatherHolderId = whId
                    it.rain?.weatherHolderId = whId
                }
                database.insertWeatherHolderChildrens(weatherHolder)
            }
            return weatherHolder
        }
        throw WrongApiResponse()
    }

    override fun getAllWeather(): WeatherModel {
        val list = database.getWeatherHolders()
        val map = list.asSequence().associateBy({it.id},{it.position})
        return WeatherModel(list, map)
    }
}