package ru.kcoder.weatherhelper.features.weather.detail.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.weather_detail_adapter_titel.view.*
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class TitleDelegate : AbsListItemAdapterDelegate<String, Any, TitleDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_detail_adapter_titel, parent, false)
        )
    }

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is String
    }

    override fun onBindViewHolder(item: String, holder: ViewHolder, p2: MutableList<Any>) {
        holder.itemView.textViewTitle.text = item
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}