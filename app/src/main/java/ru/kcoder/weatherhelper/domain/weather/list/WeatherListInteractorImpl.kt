package ru.kcoder.weatherhelper.domain.weather.list

import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils
import kotlin.math.log

class WeatherListInteractorImpl(private val weatherRepository: WeatherRepository) : BaseInteractor(),
    WeatherListInteractor {

    private val updatingList = mutableListOf<WeatherHolder>()

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

    override fun getAllWeather(callback: (WeatherModel) -> Unit) {
        loading(weatherRepository, {
            getAllWeather().also {
                createUpdatingList(it)
            }
        }, { data, error ->
            data?.let {
                callback(it)
                updateWeatherUnit(callback)
            }
            error?.let {
                log(it.message ?: it.toString())
            }
        })
    }

    private fun updateWeatherUnit(callback: (WeatherModel) -> Unit) {
        if (updatingList.isNotEmpty()) {
            val weatherHolder = updatingList[0]
            loading(weatherRepository, {
                getWeatherByCoordinate(weatherHolder.lat, weatherHolder.lon)
            }, { data, error ->
                data?.let {
                    getAllWeather { wm ->
                        callback(wm.apply { updatedWeatherHolderId = it.id })
                    }
                }
                error?.let {
                    updateWeatherUnit(callback)
                    log(it.message ?: it.toString())
                }
            })
        }
    }

    private fun createUpdatingList(model: WeatherModel) {
        val list = model.list
        updatingList.clear()
        for (weatherHolder in list) {
            val data = weatherHolder.data
            if (!data.isNullOrEmpty() && data[0].dt != null && TimeUtils.isThreeHourDifference(data[0].dt!!)) {
                updatingList.add(weatherHolder)
            }
        }
    }
}