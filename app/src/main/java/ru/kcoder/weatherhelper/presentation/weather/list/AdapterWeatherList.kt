package ru.kcoder.weatherhelper.presentation.weather.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_common.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class AdapterWeatherList(private val callback: (Long) -> Unit) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AdapterWeatherList.ViewHolder>() {

    private val list = mutableListOf<WeatherHolder>()
    private var positionMap = mutableMapOf<Long, Int>()

    fun setData(data: WeatherModel) {
        if (list.isEmpty()) {
            list.addAll(data.list)
            positionMap.putAll(data.map)
            notifyDataSetChanged()
        } else {
            val position = positionMap[data.updatedWeatherHolderId]
            if (position != null && data.list.isNotEmpty()) {
                list[position] = data.list[0]
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(root: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(root.context)
                .inflate(R.layout.weather_list_adapter_item, root, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            setOnClickListener {
                callback(list[position].id)
            }
            list[position].also {
                textViewTitle.text = it.name
                textViewTimeDescription.text = it.hours[0].dateAndDescription
                textViewTemp.text = it.hours[0].tempNow
                textViewCalvin.text = it.hours[0].degreeThumbnail
                imageViewIco.setImageResource(it.hours[0].icoRes)
                textViewHumidityDescription.text = it.hours[0].humidity
                textViewWindDescription.text = it.hours[0].wind
            }
            Unit
        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}