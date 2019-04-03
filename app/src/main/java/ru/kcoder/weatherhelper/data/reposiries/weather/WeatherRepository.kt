package ru.kcoder.weatherhelper.data.reposiries.weather

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition
import ru.kcoder.weatherhelper.toolkit.farmework.components.Async

interface WeatherRepository {
    suspend fun updateWeatherById(settings: Settings, id: Long, scope: CoroutineScope)
    fun getWeather(id: Long, async: Async): LiveData<WeatherHolder>
    fun delete(id: Long)
    fun changedData(list: List<WeatherHolder>)
    fun getDayTitle(): String
    fun getWeatherPositions(): LiveData<List<WeatherPosition>>

    fun getAllWeather(
        settings: Settings,
        async: Async
    ): LiveData<List<WeatherHolder>>

    fun setLoadingStatus(id: Long)
    fun clearStatus(id: Long)
    fun clearAllStatus()
}