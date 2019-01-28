package ru.kcoder.weatherhelper.data.entity.weather.detail

import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation

class SlimHour(
    val hour: WeatherPresentation,
    var isChecked: Boolean = false
)