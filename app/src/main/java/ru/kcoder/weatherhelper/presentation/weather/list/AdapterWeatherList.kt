package ru.kcoder.weatherhelper.presentation.weather.list

import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_common.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.ru.weatherhelper.R


class AdapterWeatherList(
    private val showDetail: (Long) -> Unit,
    private val update: (Long) -> Unit,
    private val delete: (Long, String) -> Unit
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<AdapterWeatherList.ViewHolder>() {

    private val list = mutableListOf<WeatherHolder>()
    private var positionMap = mutableMapOf<Long, Int>()
    private var isEditStatus = false

    fun setData(data: WeatherModel) {
        if (list.isEmpty() || data.updatedWeatherHolderId == 0L) {
            list.clear()
            positionMap.clear()
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

    fun updateStatus(item: Pair<Long, Boolean>) {
        positionMap[item.first]?.let { changeItem(it, list[it].apply { isUpdating = item.second }) }
    }

    fun setEditStatus(isEdit: Boolean) {
        isEditStatus = isEdit
        notifyDataSetChanged()
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
            val item = list[position]

            setOnClickListener {
                showDetail(item.id)
            }

            val hours = item.hours
            textViewTitle.text = item.name

            if (item.isUpdating){
                (imageButtonUpdate.drawable as Animatable).start()
            } else {
                (imageButtonUpdate.drawable as Animatable).stop()
            }

            imageButtonUpdate.setOnClickListener {
                if (item.isUpdating)return@setOnClickListener

                update(item.id)
                (imageButtonUpdate.drawable as Animatable).start()
                item.isUpdating = true
            }

            if (isEditStatus){
                viewDelimiter.visibility = View.VISIBLE
                buttonDelete.visibility = View.VISIBLE
            } else {
                viewDelimiter.visibility = View.GONE
                buttonDelete.visibility = View.GONE
            }

            buttonDelete.setOnClickListener {
                delete(item.id, item.name)
            }

            if (hours.isNotEmpty()) {
                bind(position, hours)
                seekBarWeather.setNames(hours.map { it.time })
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

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}