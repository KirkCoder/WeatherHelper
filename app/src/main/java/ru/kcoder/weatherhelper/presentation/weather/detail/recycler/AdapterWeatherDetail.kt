package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class AdapterWeatherDetail : ListDelegationAdapter<List<Any>>() {

    fun addDelegate(delegate: AdapterDelegate<List<Any>>) {
        delegatesManager.addDelegate(delegate)
    }

}