package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.database.settings.SettingsSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.weather.*
import ru.kcoder.weatherhelper.data.network.common.executeCall
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.kotlin.tryFormatDate

class WeatherRepositoryImpl(
    private val network: WeatherNetworkSource,
    private val database: WeatherDbSource,
    settingsSource: SettingsSource
) : WeatherRepository {

    private val settings = settingsSource.getSettings()

    override fun getWeatherById(id: Long): WeatherHolder {
        database.getSingleWeatherHolder(id)?.let {
            val weatherHolder = network
                .getWeatherByCoordinate(it.lat, it.lon, BuildConfig.API_KEY)
                .executeCall()
            weatherHolder.lat = it.lat
            weatherHolder.lon = it.lon
            weatherHolder.id = id
            weatherHolder.position = it.position
            weatherHolder.name = it.name
            database.dropOldWeatherHolderChildren(id)
            database.updateWeatherHolder(weatherHolder)
            weatherHolder.city?.weatherHolderId = id
            weatherHolder.data?.forEach {data ->
                data.weatherHolderId = id
                data.weather?.forEach { weather -> weather.weatherHolderId = id }
                data.clouds?.weatherHolderId = id
                data.sys?.weatherHolderId = id
                data.wind?.weatherHolderId = id
                data.main?.weatherHolderId = id
                data.rain?.weatherHolderId = id
            }
            database.insertWeatherHolderChildrens(weatherHolder)
            return weatherHolder
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