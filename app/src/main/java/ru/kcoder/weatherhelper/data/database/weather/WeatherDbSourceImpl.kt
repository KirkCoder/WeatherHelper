package ru.kcoder.weatherhelper.data.database.weather

import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.entity.weather.*

class WeatherDbSourceImpl(private val database: WeatherHelperRoomDb) : WeatherDbSource {

    override fun getWeatherHolders(): List<WeatherHolder> {
        val weatherHolders = database.weatherHolder().getWeatherHolders()
        for (wh in weatherHolders) {
            bindWeatherHolder(wh)
        }
        return weatherHolders
    }

    override fun updateWeatherHolder(weatherHolder: WeatherHolder) {
        database.weatherHolder().insertOrReplace(weatherHolder)
    }

    override fun dropOldWeatherHolderChildren(id: Long) {
        val weatherHolder = getWeatherHolder(id)
        if (weatherHolder != null) {
            bindWeatherHolder(weatherHolder)
            weatherHolder.city?.let {
                it.coord?.let { coord -> database.coord().delete(coord) }
                database.city().delete(it)
                Unit
            }
            weatherHolder.data?.let { dropData(it) }
        }
    }

    override fun insertWeatherHolderChildrens(weatherHolder: WeatherHolder) {

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

    override fun getWeatherHolderId(lat: Double, lon: Double): Long? {
        return database.weatherHolder().getWeatherHolderId(lat, lon)
    }

    override fun insertWeatherHolder(weatherHolder: WeatherHolder) {
        database.weatherHolder().insertOrReplace(weatherHolder)
    }

    private fun bindWeatherHolder(wh: WeatherHolder) {
        wh.city = getCity(wh.id)
        wh.data = getData(wh.id)
    }

    private fun dropData(data: List<Data>) {
        for (dt in data) {
            dt.weather?.let { weather ->
                weather.forEach { database.weather().delete(it) }
            }
            dt.clouds?.let { database.clouds().delete(it) }
            dt.sys?.let { database.sys().delete(it) }
            dt.wind?.let { database.wind().delete(it) }
            dt.main?.let { database.main().delete(it) }
            dt.rain?.let { database.rain().delete(it) }
        }
    }

    private fun getWeatherHolder(id: Long): WeatherHolder? {
        return database.weatherHolder().getWeatherHolderById(id)
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