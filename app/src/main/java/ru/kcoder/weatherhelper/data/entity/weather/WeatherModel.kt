package ru.kcoder.weatherhelper.data.entity.weather

/**
 * class provide weatherPresentation list and inform what what weatherHolderId in collectionwas updated
 * @param list List<WeatherHolderFuture>
 * @param map Map<Long, Int> sorted map where Long - WeatherHolderId, Int - position WeatherHolderFuture in list
 * @param updatedWeatherHolderId if -1 then all position new
 */
class WeatherModel(
    val list: List<WeatherHolderPresentation>,
    val map: Map<Long, Int>,
    var updatedWeatherHolderId: Long = -1L
)
