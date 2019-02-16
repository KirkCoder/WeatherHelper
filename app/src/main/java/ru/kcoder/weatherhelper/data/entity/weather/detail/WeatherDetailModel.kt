package ru.kcoder.weatherhelper.data.entity.weather.detail

data class WeatherDetailModel(
    val list: List<WeatherPosition>,
    var selectedItem: SelectedItem?
)