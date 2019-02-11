package ru.kcoder.weatherhelper.features.weather.detail.recycler

interface ClickedItem {
    fun setClick()
    fun removeClick()
    fun isClicked(): Boolean
}