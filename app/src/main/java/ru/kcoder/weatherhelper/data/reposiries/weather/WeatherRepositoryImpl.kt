package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.database.settings.SettingsSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.weather.*
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.kotlin.tryFormatDate

class WeatherRepositoryImpl(
    val network: WeatherNetworkSource,
    val database: WeatherDbSource,
    val settingsSource: SettingsSource
) : WeatherRepository {

    val settings = settingsSource.getSettings()

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

    override fun getWeatherPresentation(id: Long, update: Boolean): WeatherPresentationHolder {
        val weatherHolder = if (update) {
            getWeatherById(id)
        } else {
            database.getWeatherHolder(id) ?: getWeatherById(id)
        }
        return mapToPresentation(weatherHolder)
    }

    private fun mapToPresentation(weather: WeatherHolder): WeatherPresentationHolder {
        return WeatherPresentationHolder().apply {
            id = weather.id
            position = weather.position
            lat = weather.lat
            lon = weather.lon
            name = weather.name
            weather.data?.forEach { data ->
                hours.add(
                    getWeatherPresentation(data)
                )
            }
        }
    }

    private fun getWeatherPresentation(data: Data): WeatherPresentation {
        val weather = WeatherPresentation()

        weather.dateAndDescription = "${data.dt.tryFormatDate()} ${
        data.weather?.let {
            if (it.isNotEmpty()) it[0].description
            else ""
        }
        }"
        weather.tempNow = data.main?.temp?.let { it - settings.degreeDifference }?.toString() ?: ""
        weather.degreeThumbnail = settings.degreeThumbnail
        weather.wind = data.wind?.speed?.toInt()?.toString() ?: ""
        weather.humidity = data.main?.humidity?.toInt()?.toString() ?: ""

        return weather
    }
}