package ru.kcoder.weatherhelper.presentation.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.weather_common.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class CommonDelegate()
    : AbsListItemAdapterDelegate<WeatherPresentation, Any, CommonDelegate.CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): CommonViewHolder {
        return CommonViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_common, parent, false)
        )
    }

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is WeatherPresentation
    }

    override fun onBindViewHolder(item: WeatherPresentation, holder: CommonViewHolder, payloads: MutableList<Any>) {
        with(holder.itemView){
            textViewTimeDescription.text = item.dateAndDescription
            textViewTemp.text = item.tempNow
            textViewCalvin.text = item.degreeThumbnail
            imageViewIco.setImageResource(item.icoRes)
            textViewHumidityDescription.text = item.humidity
            textViewWindDescription.text = item.wind
        }
    }

    inner class CommonViewHolder(view: View): RecyclerView.ViewHolder(view)
}