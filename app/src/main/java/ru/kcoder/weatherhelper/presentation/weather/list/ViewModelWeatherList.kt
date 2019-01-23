package ru.kcoder.weatherhelper.presentation.weather.list

import androidx.lifecycle.LiveData
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

abstract class ViewModelWeatherList: BaseViewModel() {
    abstract val weatherList: LiveData<WeatherModel>
    abstract val updateStatus: LiveData<Pair<Long, Boolean>>
    abstract val editStatus: LiveData<Boolean>

    abstract fun addPlace(holder: WeatherHolder)
    abstract fun forceUpdate(id: Long)
    abstract fun setEditStatus(isEditStatus: Boolean)
    abstract fun delete(id: Long, list: List<WeatherHolder>)
    abstract fun changedData(list: List<WeatherHolder>)
    abstract fun notifyChange(model: WeatherModel)
}