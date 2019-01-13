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
import ru.kcoder.weatherhelper.toolkit.kotlin.*
import java.util.*

class WeatherRepositoryImpl(
    private val network: WeatherNetworkSource,
    private val database: WeatherDbSource,
    private val stringSource: WeatherStringSource,
    private val imageSource: ImageResSource,
    settingsSource: SettingsSource
) : WeatherRepository {

    private val settings = settingsSource.getSettings()

    override fun getWeatherById(id: Long): WeatherHolder {

        database.getSingleWeatherHolder(id)?.let {

            val weatherData = network
                .getWeather(it.lat, it.lon, BuildConfig.API_KEY)
                .executeCall()

            val weatherForecast = network
                .getWeatherForecast(it.lat, it.lon, BuildConfig.API_KEY)
                .executeCall()

            val time = weatherData.dt

            if (time != null) {
                it.main = getWeatherPresentation(
                    weatherData, time.addMilliseconds() + it.timeUTCoffset,
                    id, WeatherPresentation.MAIN
                )
            } else {
                it.main = getWeatherPresentation(
                    weatherData, Calendar.getInstance().time.time + it.timeUTCoffset,
                    id, WeatherPresentation.MAIN
                )
            }
            it.bindDaysAndHours(weatherForecast.data, it.timeUTCoffset, id)

            val insertion = mutableListOf<WeatherPresentation>()
            insertion.add(it.main)
            insertion.addAll(it.hours)
            insertion.addAll(it.days)
            insertion.addAll(it.nights)

            database.dropOldData(id)
            database.insertWeatherPresentations(insertion)

            return it
        } ?: throw LocalException(LocalExceptionMsg.UNEXPECTED_ERROR)
    }

    override fun getAllWeather(): WeatherModel {
        val list = database.getAllWeather()
        val mapList = list.map { it.mapToPresentation() }
        val map = mapList.asSequence().associateBy({ it.id }, { it.position })
        return WeatherModel(mapList, map)
    }

    // todo replace to mocked weatherHolder
    override fun getMockedWeather(): WeatherHolder {
        return WeatherHolder()
    }

    override fun getWeather(id: Long, update: Boolean): WeatherHolder {
        return if (update) {
            getWeatherById(id)
        } else {
            database.getWeather(id)?.let {
                return@let it.mapToPresentation()
            } ?: getWeatherById(id)
        }
    }

    private fun WeatherHolder.bindDaysAndHours(
        data: List<Data>?,
        timeUTCoffset: Int,
        holderID: Long
    ) {
        if (!data.isNullOrEmpty()) {
            data[0].dt?.let { long ->
                val time = timeUTCoffset + long.addMilliseconds()
                val startDayHour = time.tryFormatHour()
                var pos = 0
                val halfDay = 4
                var startNextDayPos = (24 - startDayHour) / 3
                var startNextNightPos = startNextDayPos + halfDay

                val iterator = data.listIterator()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    if (pos < 5) next.let {
                        hours.add(
                            getWeatherPresentation(
                                it,
                                time,
                                holderID,
                                WeatherPresentation.HOURS
                            )
                        )
                    }
                    if (pos == 0) days.add(getWeatherPresentation(next, time, holderID, WeatherPresentation.DAYS))
                    if (pos == 4) nights.add(getWeatherPresentation(next, time, holderID, WeatherPresentation.NIGHTS))
                    if (pos == startNextDayPos) days.add(
                        getWeatherPresentation(
                            next,
                            time,
                            holderID,
                            WeatherPresentation.DAYS
                        )
                    )
                        .also { startNextDayPos = startNextNightPos + halfDay }
                    if (pos == startNextNightPos) nights.add(
                        getWeatherPresentation(
                            next,
                            time,
                            holderID,
                            WeatherPresentation.NIGHTS
                        )
                    )
                        .also { startNextNightPos = startNextDayPos + halfDay }
                    else if (!iterator.hasNext()) nights.add(
                        getWeatherPresentation(
                            next,
                            time,
                            holderID,
                            WeatherPresentation.NIGHTS
                        )
                    )
                    pos++
                }
            }
        }
    }

    private fun getWeatherPresentation(
        data: Data,
        time: Long,
        holderID: Long,
        status: Int
    ): WeatherPresentation {
        val weather = WeatherPresentation(status)

        with(weather) {
            this.holderId = holderID
            dateAndDescription = "${time.tryFormatDate()}, ${
            data.weather?.let {
                if (it.isNotEmpty()) it[0].id?.let { id -> stringSource.getDescriptionByCod(id) } ?: ""
                else ""
            }
            }"
            tempNow = data.main?.temp?.let {
                it -
                        settings.degreeDifference
            }?.toInt()?.toString() ?: "XX"
            degreeThumbnail = settings.degreeThumbnail
            wind = data.wind?.speed?.toInt()?.toString() ?: ""
            wind = if (data.wind?.speed != null) {
                "${data.wind?.speed?.toInt()?.toString()} ${stringSource.getWindDescription()}"
            } else ""
            humidity = if (data.main?.humidity != null) {
                "${data.main?.humidity?.toInt()?.toString()}% ${stringSource.getHumidityDescription()}"
            } else ""
            this.time = time.tryFormatTime()
            timeLong = time
            day = time.tryFormatDay()
            val isDay = time.tryFormatHour() > settings.startNight || time.tryFormatHour() < settings.endNight
            icoRes = data.weather?.let {
                if (it.isNotEmpty()) {
                    imageSource.getImageIdByCod(it[0].id, isDay)
                } else imageSource.getImageIdByCod(null, isDay)
            } ?: imageSource.getImageIdByCod(null, isDay)
        }
        return weather
    }
}
