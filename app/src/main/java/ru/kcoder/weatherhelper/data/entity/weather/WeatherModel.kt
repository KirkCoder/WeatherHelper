package ru.kcoder.weatherhelper.data.entity.weather

/**
 * class provide weatherUpdate list and inform what what weatherHolderId in collectionwas updated
 * @param list List<WeatherForecast>
 * @param positionMap Map<Long, Int> sorted positionMap where Long - WeatherHolderId, Int - position WeatherForecast in list
 * @param updatedWeatherHolderId if -1 then all position new
 */
data class WeatherModel(
    var list: List<WeatherHolder>,
    var listMap: Map<Long, Int>,
    var positionMap: Map<Long, Int>,
    var updatedWeatherHolderId: Long = BROADCAST
){
    companion object {
        const val BROADCAST = -1L
        const val FORCE = 0L
    }
}
