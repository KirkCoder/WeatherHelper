package ru.kcoder.weatherhelper.features.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.weather_common.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimDay
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class DayDetailDelegate(
    private val unClick: (Int) -> Unit
) : AdapterDelegate<List<Any>>() {


    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_common, parent, false)
        )
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        val item = items[position]
        return item is SlimDay && item.isChecked
    }

    override fun onBindViewHolder(list: List<Any>, position: Int, holder: RecyclerView.ViewHolder, p3: MutableList<Any>) {
        val item = list[position] as SlimDay
        with(holder.itemView) {
            setOnClickListener {
                item.isChecked = false
                unClick(position)
            }
            textViewTimeDescription.text = item.day.dateAndDescription
            textViewTemp.text = item.day.tempNow
            textViewCalvin.text = item.day.degreeThumbnail
            imageViewIco.setImageResource(item.day.icoRes)
            textViewHumidityDescription.text = item.day.humidity
            textViewWindDescription.text = item.day.wind
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}