package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentationHolder
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractor
import ru.kcoder.weatherhelper.presentation.common.SingleLiveData

class ViewModelWeatherDetailImpl(
    private val interactor: WeatherDetailInteractor,
    private val id: Long,
    private val forceUpdate: Boolean
) : ViewModelWeatherDetail() {

    override val weather: MutableLiveData<WeatherPresentationHolder> = MutableLiveData()
    override val status: SingleLiveData<Boolean> = SingleLiveData()

    init {
        updateWeather(id, forceUpdate)
    }

    private fun updateWeather(whId: Long, forceUpdate: Boolean) {
        status
        interactor.updateWeather(whId, forceUpdate, {
            weather.value = it
        }, {
            if (forceUpdate) status.value = it
        }, this::errorCallback)
    }

    override fun forceUpdate(){
        updateWeather(id, true)
    }
}