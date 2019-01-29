package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.weather_detail_adapter_item.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimDay
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class DayDelegate
    : AbsListItemAdapterDelegate<SlimDay, Any, DayDelegate.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_detail_adapter_item, parent, false)
        )
    }

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is SlimDay
    }

    override fun onBindViewHolder(item: SlimDay, holder: ViewHolder, p2: MutableList<Any>) {
        with(holder.itemView){
            textViewName.text = item.day.time
            imageViewIcoDetail.setImageResource(item.day.icoRes)
            textViewTmp.text = item.day.tempNow
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}