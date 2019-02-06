package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractor
import ru.kcoder.weatherhelper.presentation.weather.detail.recycler.ClickedItem

class ViewModelWeatherDetailImpl(
    private val interactor: WeatherDetailInteractor,
    private val id: Long,
    forceUpdate: Boolean
) : ViewModelWeatherDetail(interactor) {

    override val weather = MutableLiveData<List<Any>>()
    override val status = MutableLiveData<Boolean>()
    override val checked = MutableLiveData<Int>()

    init {
        updateWeather(id, forceUpdate)
    }

    private fun updateWeather(whId: Long, forceUpdate: Boolean) {
        interactor.updateWeather(whId, forceUpdate, { list ->
            getChecked(list)?.let { checked.value = it }
            weather.value = list
        }, {
            if (forceUpdate) status.value = it
        })
    }

    private fun getChecked(list: List<Any>): Int? {
        for ((i) in list.withIndex()) {
            val item = list[i]
            if (item is ClickedItem && item.isClicked()) return i
        }
        return null
    }

    override fun clickInform(position: Int?) {
        checked.value = position
    }

    override fun forceUpdate() {
        updateWeather(id, true)
    }
}