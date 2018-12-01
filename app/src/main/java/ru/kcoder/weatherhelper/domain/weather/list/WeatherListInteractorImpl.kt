package ru.kcoder.weatherhelper.domain.weather.list

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.debug.log

class WeatherListInteractorImpl(private val weatherRepository: WeatherRepository) : BaseInteractor(),
    WeatherListInteractor {

    override fun getWeatherByCoordinat(
        lat: Double, lon: Double,
        callback: (WeatherHolder) -> Unit
    ) {
        loading(weatherRepository, {
            getWeatherByCoordinate(lat, lon)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let {
                log(it.message ?: "fail")
                it.printStackTrace()
            }
        })
    }

    override fun getAllWeather(callback: (List<WeatherHolder>) -> Unit) {
        loading(weatherRepository, {
            getAllWeather()
        }, { data, error ->
            data?.let { callback(it) }
            error?.let {
                log(it.message ?: "fail")
                it.printStackTrace()
            }
        })
    }
}