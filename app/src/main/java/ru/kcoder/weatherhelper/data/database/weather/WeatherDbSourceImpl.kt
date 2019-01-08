package ru.kcoder.weatherhelper.data.database.weather

import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.entity.weather.*

class WeatherDbSourceImpl(private val database: WeatherHelperRoomDb) : WeatherDbSource {

    override fun getWeatherHolders(): List<WeatherHolderFuture> {
        val weatherHolders = database.weatherHolder().getWeatherHolders()
        for (wh in weatherHolders) {
            bindWeatherHolder(wh)
        }
        return weatherHolders
    }

    override fun updateWeatherHolder(weatherHolder: WeatherHolderFuture) {
        database.weatherHolder().insertOrReplace(weatherHolder)
    }

    override fun dropOldWeatherHolderChildren(id: Long) {
        database.city().deleteAllByWeatherHolderId(id)
        database.clouds().deleteAllByWeatherHolderId(id)
        database.coord().deleteAllByWeatherHolderId(id)
        database.data().deleteAllByWeatherHolderId(id)
        database.main().deleteAllByWeatherHolderId(id)
        database.rain().deleteAllByWeatherHolderId(id)
        database.sys().deleteAllByWeatherHolderId(id)
        database.weather().deleteAllByWeatherHolderId(id)
        database.wind().deleteAllByWeatherHolderId(id)
    }

    override fun insertWeatherHolderChildrens(weatherHolder: WeatherHolderFuture) {
        weatherHolder.city?.let { database.city().insertOrReplace(it) }
        weatherHolder.data?.let { dt ->
            database.data().insertOrReplace(dt)
            dt.forEach { data ->
                data.weather?.let { weather -> database.weather().insertOrReplace(weather) }
                data.clouds?.let { database.clouds().insertOrReplace(it) }
                data.sys?.let { database.sys().insertOrReplace(it) }
                data.wind?.let { database.wind().insertOrReplace(it) }
                data.main?.let { database.main().insertOrReplace(it) }
                data.rain?.let { database.rain().insertOrReplace(it) }
            }
        }
    }

    override fun getLastPosition(): Int? {
        return database.weatherHolder().getLastPosition()
    }

    override fun getWeatherHolderPosition(id: Long): Int {
        return database.weatherHolder().getWeatherHolderPosition(id)
    }

    override fun getWeatherHolderId(lat: Double, lon: Double): Long? {
        return database.weatherHolder().getWeatherHolderId(lat, lon)
    }

    override fun insertWeatherHolder(weatherHolder: WeatherHolderFuture) {
        database.weatherHolder().insertOrReplace(weatherHolder)
    }

    override fun getSingleWeatherHolder(id: Long): WeatherHolderFuture? {
        return database.weatherHolder().getWeatherHolderById(id)
    }

    override fun getWeatherHolder(id: Long): WeatherHolderFuture? {
        return database.weatherHolder().getWeatherHolderById(id)?.also {
            bindWeatherHolder(it)
        }
    }

    private fun bindWeatherHolder(wh: WeatherHolderFuture) {
        wh.city = getCity(wh.id)
        wh.data = getData(wh.id)
    }

    private fun getData(weatherHolderId: Long): List<Data> {
        val data = database.data().getDataByWeatherHolderId(weatherHolderId)
        for (dt in data) {
            dt.weather = getWeather(dt.weatherHolderId)
            dt.clouds = getClouds(dt.weatherHolderId)
            dt.sys = getSys(dt.weatherHolderId)
            dt.wind = getWind(dt.weatherHolderId)
            dt.main = getMain(dt.weatherHolderId)
            dt.rain = getRain(dt.weatherHolderId)
        }
        return data
    }

    private fun getCity(weatherHolderId: Long): City? {
        val city = database.city().getCityByWeatherHolderId(weatherHolderId)
        city?.coord = getCoord(weatherHolderId)
        return city
    }

    private fun getCoord(weatherHolderId: Long): Coord? {
        return database.coord().getCoordByWeatherHolderId(weatherHolderId)
    }

    private fun getWeather(weatherHolderId: Long): List<Weather> {
        return database.weather().getWeatherByWeatherHolderId(weatherHolderId)
    }

    private fun getClouds(weatherHolderId: Long): Clouds? {
        return database.clouds().getCloudsByWeatherHolderId(weatherHolderId)
    }

    private fun getSys(weatherHolderId: Long): Sys? {
        return database.sys().getSysByWeatherHolderId(weatherHolderId)
    }

    private fun getWind(weatherHolderId: Long): Wind? {
        return database.wind().getWindByWeatherHolderId(weatherHolderId)
    }

    private fun getMain(weatherHolderId: Long): Main? {
        return database.main().getMainByWeatherHolderId(weatherHolderId)
    }

    private fun getRain(weatherHolderId: Long): Rain? {
        return database.rain().getRainByWeatherHolderId(weatherHolderId)
    }
}