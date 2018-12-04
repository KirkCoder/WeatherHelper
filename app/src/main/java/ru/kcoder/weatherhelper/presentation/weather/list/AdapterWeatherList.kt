package ru.kcoder.weatherhelper.presentation.weather.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_list_adapter_item.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class AdapterWeatherList(private val callback: (Long) -> Unit) : RecyclerView.Adapter<AdapterWeatherList.ViewHolder>() {

    private val list = mutableListOf<WeatherHolder>()

    fun setData(data: List<WeatherHolder>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
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
            textViewTitle.text = list[position].city?.name
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}