package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

interface ClickedItem {
    fun setClick()
    fun removeClick()
    fun isClicked(): Boolean
}