package ru.kcoder.weatherhelper.features.weather.list.adapter

import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.weather_list_adapter_main.*
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
        (holder as MainViewHolder).bind(items[position], showDetail, update)
    }


    class MainViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(
            item: WeatherHolder,
            showDetail: (Long) -> Unit,
            update: (Long) -> Unit
        ) {
            cardView.setOnClickListener(null)
            seekBarWeather.setListener(null)

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

            cardView.setOnClickListener { showDetail(item.id) }

            if (hours.isNotEmpty()) {
                bindLocal(0, hours)
                seekBarWeather.setNames(item.timeNames)
                seekBarWeather.setListener {
                    bindLocal(it, hours)
                }
            }
        }

        private fun bindLocal(
            hourPosition: Int,
            hours: MutableList<WeatherPresentation>
        ) {
            textViewTimeDescription.text = hours[hourPosition].dateAndDescription
            textViewTemp.text = hours[hourPosition].tempNow
            textViewCalvin.text = hours[hourPosition].degreeThumbnail
            imageViewIco.setImageResource(hours[hourPosition].icoResColored)
            textViewHumidityDescription.text = hours[hourPosition].humidity
            textViewWindDescription.text = hours[hourPosition].wind
        }
    }
}