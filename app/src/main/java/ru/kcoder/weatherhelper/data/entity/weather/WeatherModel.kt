package ru.kcoder.weatherhelper.data.entity.weather

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder

/**
 * class provide weather list and inform what what weatherHolderId in collectionwas updated
 * @param list List<WeatherHolder>
 * @param map Map<Long, Int> sorted map where Long - WeatherHolderId, Int - position WeatherHolder in list
 * @param updatedWeatherHolderId if -1 then all position new
 */
class WeatherModel(
    val list: List<WeatherPresentationHolder>,
    val map: Map<Long, Int>,
    var updatedWeatherHolderId: Long = -1L
)
