package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.presentation.common.BaseInteractor
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelWeatherDetail(
    baseInteractor: BaseInteractor
) : BaseViewModel(baseInteractor) {
    abstract val weather: LiveData<List<Any>>
    abstract val status: LiveData<Boolean>
    abstract val checked: LiveData<Int>
    abstract fun forceUpdate()
}