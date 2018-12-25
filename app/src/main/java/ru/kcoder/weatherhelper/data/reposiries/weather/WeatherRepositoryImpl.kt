package ru.kcoder.weatherhelper.data.reposiries.weather

import ru.kcoder.weatherhelper.data.database.settings.SettingsSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.weather.*
import ru.kcoder.weatherhelper.data.network.common.executeCall
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.data.resourses.imageres.ImageResSource
import ru.kcoder.weatherhelper.data.resourses.string.WeatherStringSource
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.kotlin.getHour
import ru.kcoder.weatherhelper.toolkit.kotlin.tryFormatDate
import ru.kcoder.weatherhelper.toolkit.kotlin.tryFormatDay
import ru.kcoder.weatherhelper.toolkit.kotlin.tryFormatTime

class WeatherRepositoryImpl(
    private val network: WeatherNetworkSource,
    private val database: WeatherDbSource,
    settingsSource: SettingsSource,
    private val stringSource: WeatherStringSource,
    private val imageSource: ImageResSource
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
            weatherHolder.data?.forEach { data ->
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

    override fun getWeatherPresentationHolder(id: Long, update: Boolean): WeatherPresentationHolder {
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
            val data = weather.data
            bindDaysAndHours(data)
        }
    }

    private fun WeatherPresentationHolder.bindDaysAndHours(data: List<Data>?) {
        if (!data.isNullOrEmpty()) {
            data[0].dt?.let { long ->
                val startDayHour = long.getHour()
                var pos = 0
                val halfDay = 4
                var startNextDayPos = (24 - startDayHour) / 3
                var startNextNightPos = startNextDayPos + halfDay

                val iterator = data.listIterator()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    if (pos < 5) next.let { hours.add(getWeatherPresentation(it)) }
                    if (pos == 0) days.add(getWeatherPresentation(next))
                    if (pos == 4) nights.add(getWeatherPresentation(next))
                    if (pos == startNextDayPos) days.add(getWeatherPresentation(next))
                        .also { startNextDayPos = startNextNightPos + halfDay }
                    if (pos == startNextNightPos) nights.add(getWeatherPresentation(next))
                        .also { startNextNightPos = startNextDayPos + halfDay }
                    else if (!iterator.hasNext()) nights.add(getWeatherPresentation(next))
                    pos++
                }
            }
        }
    }

    private fun getWeatherPresentation(data: Data): WeatherPresentation {
        val weather = WeatherPresentation()

        with(weather) {
            dateAndDescription = "${data.dt.tryFormatDate()}, ${
            data.weather?.let {
                if (it.isNotEmpty()) it[0].id?.let { id -> stringSource.getDescriptionByCod(id) } ?: ""
                else ""
            }
            }"
            tempNow = data.main?.temp?.let { it -
                    settings.degreeDifference }?.toInt()?.toString() ?: "XX"
            degreeThumbnail = settings.degreeThumbnail
            wind = data.wind?.speed?.toInt()?.toString() ?: ""
            wind = if (data.wind?.speed != null) {
                "${data.wind?.speed?.toInt()?.toString()} ${stringSource.getWindDescription()}"
            } else ""
            humidity = if (data.main?.humidity != null) {
                "${data.main?.humidity?.toInt()?.toString()}% ${stringSource.getHumidityDescription()}"
            } else ""
            time = data.dt.tryFormatTime()
            day = data.dt.tryFormatDay()
            val isDay = data.dt.getHour() > settings.startNight || data.dt.getHour() < settings.endNight
            icoRes = data.weather?.let {
                if (it.isNotEmpty()) {
                    imageSource.getImageIdByCod(it[0].id, isDay)
                } else imageSource.getImageIdByCod(null, isDay)
            } ?: imageSource.getImageIdByCod(null, isDay)
        }

        return weather
    }
}