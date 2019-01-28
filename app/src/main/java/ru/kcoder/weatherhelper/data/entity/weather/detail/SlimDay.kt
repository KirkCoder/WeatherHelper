package ru.kcoder.weatherhelper.data.entity.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation

data class SlimDay(
    val day: WeatherPresentation,
    val night: WeatherPresentation,
    val isChecked: Boolean = false
)