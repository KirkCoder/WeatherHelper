package ru.kcoder.weatherhelper.data.reposiries.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.*
import ru.kcoder.weatherhelper.data.entity.weather.network.Data
import ru.kcoder.weatherhelper.data.network.common.executeCall
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.data.resourses.imageres.ImageResSource
import ru.kcoder.weatherhelper.data.resourses.string.WeatherStringSource
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.kotlin.*
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils

class WeatherRepositoryImpl(
    private val network: WeatherNetworkSource,
    private val database: WeatherDbSource,
    private val stringSource: WeatherStringSource,
    private val imageSource: ImageResSource
) : WeatherRepository {

    private val allWeatherLiveData = MediatorLiveData<List<WeatherHolder>>()
    private val weatherLiveData = MediatorLiveData<WeatherHolder>()

    override fun updateWeatherById(settings: Settings, id: Long) {

        database.getSingleWeatherHolder(id)?.let {
            val weatherData = network
                .getWeather(it.lat, it.lon, BuildConfig.API_KEY)
                .executeCall()

            val weatherForecast = network
                .getWeatherForecast(it.lat, it.lon, BuildConfig.API_KEY)
                .executeCall()

            it.bindData(settings, weatherData, weatherForecast.data, it.timeUTCoffset, id)

            val insertion = mutableListOf<WeatherPresentation>()
            insertion.addAll(it.hours)
            insertion.addAll(it.days)
            insertion.addAll(it.nights)

            database.updateWeatherPresentations(it, insertion)

        } ?: throw LocalException(LocalExceptionMsg.UNEXPECTED_ERROR)
    }

    override fun getAllWeather(
        settings: Settings,
        scope: CoroutineScope
    ): LiveData<List<WeatherHolder>> {
        allWeatherLiveData.addSource(database.getAllWeatherLd()) { list ->
            list?.let { nList ->
                scope.launch(Dispatchers.IO) {
                    allWeatherLiveData.postValue(
                        nList.map { it.mapToPresentation() }.sortedBy { it.position }
                    )
                }
            }
        }
        return allWeatherLiveData
    }

    override fun getWeather(id: Long, scope: CoroutineScope): LiveData<WeatherHolder> {
        weatherLiveData.addSource(database.getWeather(id)) { holder ->
            holder?.let { nHolder ->
                scope.launch(Dispatchers.IO) {
                    weatherLiveData.postValue(nHolder.mapToPresentation())
                }
            }
        }
        return weatherLiveData
    }

    override fun getDayTitle(): String {
        return stringSource.getDayTitle()
    }

    override fun delete(id: Long) {
        database.deleteWeatherHolder(id)
    }

    override fun changedData(list: List<WeatherHolder>) {
        database.changePositions(list)
    }

    override fun getWeatherPositions() = database.getWeatherPositions()

    private fun WeatherHolder.bindData(
        settings: Settings,
        main: Data,
        data: List<Data>?,
        timeUTCoffset: Int,
        holderID: Long
    ) {
        val tmpMainTime = main.dt

        isUpdating = false

        lustUpdate = if (tmpMainTime != null) {
            tmpMainTime.addMilliseconds() + timeUTCoffset
        } else {
            TimeUtils.getCurrentUtcTime() + timeUTCoffset
        }

        hours.add(
            getWeatherPresentation(
                settings,
                main,
                lustUpdate,
                holderID,
                WeatherPresentation.HOURS
            )
        )

        if (!data.isNullOrEmpty()) {
            data[0].dt?.let { long ->
                val time = timeUTCoffset + long.addMilliseconds()
                val startDayHour = time.tryFormatHour()
                var pos = 0
                val halfDay = 4
                val difference = (startDayHour - 12) / settings.serverTimeJump
                var startNextDayPos = (24 / settings.serverTimeJump) - difference
                var startNextNightPos = startNextDayPos + halfDay

                val iterator = data.listIterator()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    next.dt?.let { tmpLong ->
                        val tmpTime = timeUTCoffset + tmpLong.addMilliseconds()
                        if (pos < settings.maxHourPoints) {
                            hours.add(
                                getWeatherPresentation(
                                    settings,
                                    next,
                                    tmpTime,
                                    holderID,
                                    WeatherPresentation.HOURS
                                )
                            )
                        }
                        if (pos == 0) days.add(
                            getWeatherPresentation(
                                settings,
                                next,
                                tmpTime,
                                holderID,
                                WeatherPresentation.DAYS
                            )
                        )
                        if (pos == 4) nights.add(
                            getWeatherPresentation(settings, next, tmpTime, holderID, WeatherPresentation.NIGHTS)
                        )
                        if (pos == startNextDayPos) days.add(
                            getWeatherPresentation(
                                settings,
                                next,
                                tmpTime,
                                holderID,
                                WeatherPresentation.DAYS
                            )
                        )
                            .also { startNextDayPos = startNextNightPos + halfDay }
                        if (pos == startNextNightPos) {
                            nights.add(
                                getWeatherPresentation(
                                    settings,
                                    next,
                                    tmpTime,
                                    holderID,
                                    WeatherPresentation.NIGHTS
                                )
                            ).also { startNextNightPos = startNextDayPos + halfDay }
                        } else if (!iterator.hasNext()) {
                            nights.add(
                                getWeatherPresentation(
                                    settings,
                                    next,
                                    tmpTime,
                                    holderID,
                                    WeatherPresentation.NIGHTS
                                )
                            )
                        }
                        Unit
                    }
                    pos++
                }
            }
        }
    }

    private fun getWeatherPresentation(
        settings: Settings,
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
                it - settings.degreeDifference
            }?.toInt()?.toString() ?: "XX"
            degreeThumbnail = settings.degreeThumbnail
            tempNowWithThumbnail = "$tempNow$degreeThumbnail"
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
            val tmpTime = time.tryFormatHour()
            val isDay = tmpTime > settings.startNight || tmpTime < settings.endNight
            icoRes = data.weather?.let {
                if (it.isNotEmpty()) {
                    imageSource.getImageIdByCod(it[0].id, isDay)
                } else imageSource.getImageIdByCod(null, isDay)
            } ?: imageSource.getImageIdByCod(null, isDay)
        }
        return weather
    }

    override fun setLoadingStatus(id: Long) {
        database.setLoadingStatus(id)
    }

    override fun clearStatus(id: Long) {
        database.clearStatus(id)
    }

    override fun clearAllStatus() = database.clearAllStatus()
}
