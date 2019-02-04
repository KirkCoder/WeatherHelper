package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class AdapterWeatherDetail : ListDelegationAdapter<List<Any>>() {

    private var clicked: Int? = null

    init {
        delegatesManager.addDelegate(CommonDelegate(this))
            .addDelegate(DayDelegate())
            .addDelegate(TitleDelegate())
            .addDelegate(MainTitleDelegate())
            .addDelegate(HoursDelegate(this))
    }

    fun setData(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }

    private fun onDetailClick(position: Int){
        clicked?.let {
            (items[it] as? ClickedItem)?.removeClick()
            notifyItemChanged(it)
        }
        setClicked(position)
    }

    private fun setClicked(position: Int) {
        clicked = position
        notifyItemChanged(position)
    }

    fun setChecked(position: Int) {
        clicked = position
    }

}