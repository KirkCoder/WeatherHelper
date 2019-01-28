package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.weather_detail_adapter_item.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimHour
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class HoursDelegate:
    AbsListItemAdapterDelegate<SlimHour, Any, HoursDelegate.HourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): HourViewHolder {
        return HourViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_detail_adapter_item, parent)
        )
    }

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is SlimHour
    }

    override fun onBindViewHolder(item: SlimHour, holder: HourViewHolder, p2: MutableList<Any>) {
        with(holder.itemView){
            textViewName.text = item.hour.time
            imageViewIco.setImageResource(item.hour.icoRes)
            textViewTmp.text = item.hour.tempNow
        }
    }

    inner class HourViewHolder(view: View): RecyclerView.ViewHolder(view)
}