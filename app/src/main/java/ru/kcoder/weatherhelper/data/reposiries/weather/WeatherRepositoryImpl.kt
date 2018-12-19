package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.domain.weather.list.WeatherModel
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg

class WeatherRepositoryImpl(
    val network: WeatherNetworkSource,
    val database: WeatherDbSource
) : WeatherRepository {

    override fun getWeatherById(id: Long): WeatherHolder {
        database.getSingleWeatherHolder(id)?.let {
            val weatherHolder = network.getWeatherByCoordinate(it.lat, it.lon, BuildConfig.API_KEY)
                .execute().body()
            if (weatherHolder?.cod != null && weatherHolder.cod.equals("200")) {
                weatherHolder.lat = it.lat
                weatherHolder.lon = it.lon
                weatherHolder.id = id
                weatherHolder.position = it.position
                weatherHolder.name = it.name
                database.dropOldWeatherHolderChildren(id)
                database.updateWeatherHolder(weatherHolder)
                weatherHolder.city?.weatherHolderId = id
                weatherHolder.data?.forEach {
                    it.weatherHolderId = id
                    it.weather?.forEach { weather -> weather.weatherHolderId = id }
                    it.clouds?.weatherHolderId = id
                    it.sys?.weatherHolderId = id
                    it.wind?.weatherHolderId = id
                    it.main?.weatherHolderId = id
                    it.rain?.weatherHolderId = id
                }
                database.insertWeatherHolderChildrens(weatherHolder)
                return weatherHolder
            } else throw LocalException(LocalExceptionMsg.CANT_CONNECT)
        } ?: throw LocalException(LocalExceptionMsg.UNEXPECTED_ERROR)
    }

    override fun getAllWeather(): WeatherModel {
        val list = database.getWeatherHolders()
        val map = list.asSequence().associateBy({ it.id }, { it.position })
        return WeatherModel(list, map)
    }
}