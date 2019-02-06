package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.weather_detail_adapter_item.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimHour
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class HoursDelegate(
    private val click: (Int) -> Unit
) : AdapterDelegate<List<Any>>() {

    override fun onCreateViewHolder(parent: ViewGroup): HourViewHolder {
        return HourViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_detail_adapter_item, parent, false)
        )
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        val item = items[position]
        return item is SlimHour && !item.isChecked
    }

    override fun onBindViewHolder(
        items: List<Any>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        list: MutableList<Any>
    ) {
        val item = items[position] as SlimHour
        with(holder.itemView) {
            setOnClickListener {
                item.isChecked = true
                click(position)
            }
            textViewName.text = item.hour.time
            imageViewIcoDetail.setImageResource(item.hour.icoRes)
            textViewTmp.text = item.hour.tempNowWithThumbnail
            textViewDescription.text = item.hour.dateAndDescription
        }
    }

    inner class HourViewHolder(view: View) : RecyclerView.ViewHolder(view)
}