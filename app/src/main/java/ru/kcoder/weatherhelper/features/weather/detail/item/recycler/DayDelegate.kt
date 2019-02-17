package ru.kcoder.weatherhelper.features.weather.detail.item.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.weather_detail_adapter_item.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.SlimDay
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class DayDelegate(
    private val clicked: (Int) -> Unit
) : AdapterDelegate<List<Any>>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_detail_adapter_item, parent, false)
        )
    }

    override fun isForViewType(items: List<Any>, position: Int): Boolean {
        val item = items[position]
        return item is SlimDay && !item.isChecked
    }

    override fun onBindViewHolder(items: List<Any>, position: Int, holder: RecyclerView.ViewHolder, p3: MutableList<Any>) {
        val item = items[position] as SlimDay
        with(holder.itemView) {
            setOnClickListener {
                item.isChecked = true
                clicked(position)
            }
            textViewName.text = item.day.day
            imageViewIcoDetail.setImageResource(item.day.icoRes)
            textViewTmp.text = item.commonTmp
            textViewDescription.text = item.day.dateAndDescription
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}