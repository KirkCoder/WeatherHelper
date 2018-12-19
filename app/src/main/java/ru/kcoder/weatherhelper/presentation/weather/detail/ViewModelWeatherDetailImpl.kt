package ru.kcoder.weatherhelper.presentation.weather.detail

import android.arch.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractor

class ViewModelWeatherDetailImpl(
    private val interactor: WeatherDetailInteractor
) : ViewModelWeatherDetail() {

    override val weather: MutableLiveData<WeatherHolder> = MutableLiveData()

    override fun updateWeather(whId: Long) {
        interactor.updateWeather(whId, {
            weather.value = it
        }, {

        }, this::errorCallback)
    }
}