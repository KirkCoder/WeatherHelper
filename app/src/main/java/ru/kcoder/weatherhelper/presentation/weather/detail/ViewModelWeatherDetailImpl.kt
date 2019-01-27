package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractor

class ViewModelWeatherDetailImpl(
    private val interactor: WeatherDetailInteractor,
    private val id: Long,
    forceUpdate: Boolean
) : ViewModelWeatherDetail(interactor) {

    override val weather: MutableLiveData<WeatherHolder> = MutableLiveData()
    override val status: MutableLiveData<Boolean> = MutableLiveData()

    init {
        updateWeather(id, forceUpdate)
    }

    private fun updateWeather(whId: Long, forceUpdate: Boolean) {
        interactor.updateWeather(whId, forceUpdate, {
            weather.value = it
        }, {
            if (forceUpdate) status.value = it
        })
    }

    override fun forceUpdate() {
        updateWeather(id, true)
    }
}