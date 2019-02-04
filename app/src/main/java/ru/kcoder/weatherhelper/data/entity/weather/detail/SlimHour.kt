package ru.kcoder.weatherhelper.data.entity.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.presentation.weather.detail.recycler.ClickedItem

class SlimHour(
    val hour: WeatherPresentation,
    var isChecked: Boolean = false
) : ClickedItem {
    override fun setClick() {
        isChecked = true
    }

    override fun removeClick() {
        isChecked = false
    }

    override fun isClicked() = isChecked
}