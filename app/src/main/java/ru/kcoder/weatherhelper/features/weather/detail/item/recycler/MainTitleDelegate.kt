package ru.kcoder.weatherhelper.features.weather.detail.item.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import kotlinx.android.synthetic.main.weather_detail_adapter_main_title.view.*
import ru.kcoder.weatherhelper.data.entity.weather.detail.MainTitle
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class MainTitleDelegate : AbsListItemAdapterDelegate<MainTitle, Any, MainTitleDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_detail_adapter_main_title, parent, false)
        )
    }

    override fun isForViewType(item: Any, items: MutableList<Any>, position: Int): Boolean {
        return item is MainTitle
    }

    override fun onBindViewHolder(item: MainTitle, holder: ViewHolder, p2: MutableList<Any>) {
        holder.itemView.textViewTitle.text = item.title
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}