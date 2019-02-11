package ru.kcoder.weatherhelper.features.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.weather_common.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimHour
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class HourDetailDelegate(
    private val unClick: (Int) -> Unit
) : AdapterDelegate<List<Any>>() {

    override fun onCreateViewHolder(parent: ViewGroup): CommonViewHolder {
        return CommonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_common, parent, false)
        )
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        val item = items[position]
        return item is SlimHour && item.isChecked
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
                item.isChecked = false
                unClick(position)
            }
            textViewTimeDescription.text = item.hour.dateAndDescription
            textViewTemp.text = item.hour.tempNow
            textViewCalvin.text = item.hour.degreeThumbnail
            imageViewIco.setImageResource(item.hour.icoRes)
            textViewHumidityDescription.text = item.hour.humidity
            textViewWindDescription.text = item.hour.wind
        }
    }

    inner class CommonViewHolder(view: View) : RecyclerView.ViewHolder(view)
}