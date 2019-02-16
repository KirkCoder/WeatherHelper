package ru.kcoder.weatherhelper.features.weather.detail.item.recycler

import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter

class AdapterWeatherDetail(
    private val clickInform: (Int?) -> Unit
) : ListDelegationAdapter<List<Any>>() {

    private var clicked: Int? = null

    init {
        delegatesManager.addDelegate(
            HourDetailDelegate(
                this::unClicked
            )
        )
            .addDelegate(DayDelegate(this::onDetailClick))
            .addDelegate(DayDetailDelegate(this::unClicked))
            .addDelegate(TitleDelegate())
            .addDelegate(HoursDelegate(this::onDetailClick))
    }

    fun setData(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }

    private fun onDetailClick(position: Int) {
        clicked?.let {
            (items[it] as? ClickedItem)?.removeClick()
            notifyItemChanged(it)
        }
        setClicked(position)
    }

    private fun unClicked(position: Int) {
        clicked = null
        clickInform(null)
        notifyItemChanged(position)
    }

    private fun setClicked(position: Int) {
        clicked = position
        clickInform(position)
        notifyItemChanged(position)
    }

    fun setChecked(position: Int) {
        clicked = position
    }

}