package ru.kcoder.weatherhelper.features.weather.detail.item.recycler

interface ClickedItem {
    fun setClick()
    fun removeClick()
    fun isClicked(): Boolean
}