package ru.kcoder.weatherhelper.features.weather.list.adapter

import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.weather_list_adapter_main.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class WeatherMainDelegate(
    private val adapterSupervisor: AdapterSupervisor,
    private val showDetail: (Long) -> Unit,
    private val update: (Long) -> Unit
) : AdapterDelegate<List<WeatherHolder>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MainViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.weather_list_adapter_main, parent, false)
        )
    }

    override fun isForViewType(items: List<WeatherHolder>, position: Int) = !adapterSupervisor.isEditStatus()

    override fun onBindViewHolder(
        items: List<WeatherHolder>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        p3: MutableList<Any>
    ) {
        with(holder.itemView) {
            setOnClickListener(null)
            seekBarWeather.setListener(null)

            val item = items[position]

            val hours = item.hours
            textViewTitle.text = item.name

            if (item.isUpdating) {
                (imageButtonUpdate.drawable as Animatable).start()
            } else {
                (imageButtonUpdate.drawable as Animatable).stop()
            }

            imageButtonUpdate.setOnClickListener {
                if (item.isUpdating) return@setOnClickListener

                update(item.id)
                (imageButtonUpdate.drawable as Animatable).start()
                item.isUpdating = true
            }

            setOnClickListener { showDetail(item.id) }

            if (hours.isNotEmpty()) {
                bind(0, hours)
                seekBarWeather.setNames(item.timeNames)
                seekBarWeather.setListener {
                    bind(it, hours)
                }
            }
            Unit
        }
    }

    private fun View.bind(
        hourPosition: Int,
        hours: MutableList<WeatherPresentation>
    ) {
        textViewTimeDescription.text = hours[hourPosition].dateAndDescription
        textViewTemp.text = hours[hourPosition].tempNow
        textViewCalvin.text = hours[hourPosition].degreeThumbnail
        imageViewIco.setImageResource(hours[hourPosition].icoRes)
        textViewHumidityDescription.text = hours[hourPosition].humidity
        textViewWindDescription.text = hours[hourPosition].wind
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view)
}