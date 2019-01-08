package ru.kcoder.weatherhelper.domain.weather.list

import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderPresentation
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.TimeUtils

class WeatherListInteractorImpl(private val weatherRepository: WeatherRepository) : BaseInteractor(),
    WeatherListInteractor {

    private val updatingList = mutableListOf<WeatherHolderPresentation>()

    override fun getAllWeather(
        callback: (WeatherModel) -> Unit,
        errorCallback: ((Int) -> Unit)?
    ) {
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
                errorCallback?.invoke(it.msg.resourceString)
            }
        })
    }

    private fun updateWeatherUnit(callback: (WeatherModel) -> Unit) {
        if (updatingList.isNotEmpty()) {
            val weatherHolder = updatingList[0]
            loading(weatherRepository, {
                getWeatherById(weatherHolder.id)
            }, { data, error ->
                data?.let {
                    getAllWeather({ wm ->
                        callback(wm.apply { updatedWeatherHolderId = it.id })
                    })
                }
                error?.let {
                    log(it.message ?: it.toString())
                    updateWeatherUnit(callback)
                }
            })
        }
    }

    private fun createUpdatingList(model: WeatherModel) {
        val list = model.list
        updatingList.clear()
        for (holder in list) {
            val data = holder.hours
            if (!data.isNullOrEmpty()
                && TimeUtils.isThreeHourDifference(data[0].timeLong)
            ) {
                updatingList.add(holder)
            }
        }
    }
}