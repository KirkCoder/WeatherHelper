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
            val itemPos = data.map[data.updatedWeatherHolderId]
            val item = itemPos?.let { data.list[it] }

            if (data.list.isNotEmpty() && itemPos != null && item != null) {
                if (position != null) {
                    changeItem(position, item)
                } else {
                    list.add(item)
                    positionMap[item.id] = list.size - 1
                    notifyItemChanged(list.size - 1)
                }
            }
        }
    }

    fun updateUnit(holder: WeatherHolder) {
        positionMap[holder.id]?.let { changeItem(it, holder) }
    }

    private fun changeItem(position: Int, item: WeatherHolder) {
        list.removeAt(position)
        list.add(position, item)
        notifyItemChanged(position)
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
                textViewTimeDescription.text = it.main.dateAndDescription
                textViewTemp.text = it.main.tempNow
                textViewCalvin.text = it.main.degreeThumbnail
                imageViewIco.setImageResource(it.main.icoRes)
                textViewHumidityDescription.text = it.main.humidity
                textViewWindDescription.text = it.main.wind
            }

            seekBarWeather.setNames(list[position].hours.map { it.time })

            Unit

        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}