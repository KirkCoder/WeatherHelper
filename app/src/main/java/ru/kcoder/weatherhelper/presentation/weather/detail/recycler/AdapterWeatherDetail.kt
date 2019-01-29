package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class AdapterWeatherDetail : ListDelegationAdapter<List<Any>>() {

    init {
        delegatesManager.addDelegate(CommonDelegate(this))
            .addDelegate(DayDelegate())
            .addDelegate(HoursDelegate(this))
    }

    fun setData(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }
}