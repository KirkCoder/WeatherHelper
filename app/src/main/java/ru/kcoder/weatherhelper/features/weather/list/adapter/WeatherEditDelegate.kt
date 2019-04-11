package ru.kcoder.weatherhelper.features.weather.list.adapter

import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import kotlinx.android.synthetic.main.weather_list_adapter_edit.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.ru.weatherhelper.R
//import kotlinx.android.extensions.LayoutContainer

class WeatherEditDelegate(
    private val adapterSupervisor: AdapterSupervisor,
    private val delete: (Long, String) -> Unit,
    private val motion: (RecyclerView.ViewHolder) -> Unit
) : AdapterDelegate<List<WeatherHolder>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return EditViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.weather_list_adapter_edit, parent, false)
        )
    }

    override fun isForViewType(items: List<WeatherHolder>, position: Int) = adapterSupervisor.isEditStatus()

    override fun onBindViewHolder(
        items: List<WeatherHolder>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        p3: MutableList<Any>
    ) {
        with(holder.itemView) {
            val item = items[position]
            textViewTitle.text = item.name
            if (item.isUpdating) {
                (imageButtonUpdate.drawable as Animatable).start()
            } else {
                (imageButtonUpdate.drawable as Animatable).stop()
            }
            if (item.hours.isNotEmpty()) {
                textViewTimeDescription.text = item.hours[0].dateAndDescription
            }

            buttonDelete.setOnClickListener { delete(item.id, item.name) }

            imageViewMotion.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
                    motion(holder)
                }
                return@setOnTouchListener false
            }
        }
    }

    class EditViewHolder(view: View) : RecyclerView.ViewHolder(view)
}