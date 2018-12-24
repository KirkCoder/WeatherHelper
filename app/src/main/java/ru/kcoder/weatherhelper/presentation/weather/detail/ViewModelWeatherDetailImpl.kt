package ru.kcoder.weatherhelper.presentation.weather.detail

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentationHolder
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractor
import ru.kcoder.weatherhelper.presentation.common.SingleLiveData

class ViewModelWeatherDetailImpl(
    private val interactor: WeatherDetailInteractor
) : ViewModelWeatherDetail() {

    override val weather: MutableLiveData<WeatherPresentationHolder> = MutableLiveData()
    override val status: SingleLiveData<Int> = SingleLiveData()

    override fun updateWeather(whId: Long, forceUpdate: Boolean) {
        interactor.updateWeather(whId, forceUpdate, {
            weather.value = it
        }, {
            if (forceUpdate) status.value = it
        }, this::errorCallback)
    }
}