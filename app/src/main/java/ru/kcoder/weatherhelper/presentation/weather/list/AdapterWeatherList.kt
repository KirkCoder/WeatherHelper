package ru.kcoder.weatherhelper.presentation.weather.list

import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_list_adapter_item.view.*
import ru.kcoder.weatherhelper.data.entity.weather.WeatherModel
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import ru.kcoder.weatherhelper.data.entity.weather.WeatherPresentation
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import java.util.*

class AdapterWeatherList(
    private val showDetail: (Long) -> Unit,
    private val update: (Long) -> Unit,
    private val delete: (Long, String) -> Unit,
    private val change: (List<WeatherHolder>) -> Unit,
    private val notifyChange: (WeatherModel) -> Unit,
    private val motion: (RecyclerView.ViewHolder) -> Unit
) : RecyclerView.Adapter<AdapterWeatherList.ViewHolder>() {

    private var list = mutableListOf<WeatherHolder>()
    private var listMap = mutableMapOf<Long, Int>()
    private var isEditStatus = false
    private var isMoved = false

    fun setData(data: WeatherModel) {
        if (list.isEmpty() || data.updatedWeatherHolderId == WeatherModel.FORCE) {
            list.clear()
            list.addAll(data.list)
            listMap.clear()
            listMap.putAll(data.listMap)
            notifyDataSetChanged()
        } else {
            val position = listMap[data.updatedWeatherHolderId]
            val itemPos = data.listMap[data.updatedWeatherHolderId]
            val item = itemPos?.let { data.list[it] }

            if (data.list.isNotEmpty() && itemPos != null && item != null) {
                if (position != null) {
                    changeItem(position, item)
                } else {
                    list.add(item)
                    listMap[item.id] = list.size - 1
                    notifyItemChanged(list.size - 1)
                }
            }
        }
    }

    fun updateStatus(item: Pair<Long, Boolean>) {
        listMap[item.first]?.let { changeItem(it, list[it].apply { isUpdating = item.second }) }
    }

    private fun changeItem(position: Int, holder: WeatherHolder) {
        list.removeAt(position)
        list.add(position, holder)
        notifyItemChanged(position)
    }

    fun setEditStatus(isEdit: Boolean) {
        isEditStatus = isEdit
        notifyDataSetChanged()
        if (!isEdit && isMoved) change(list)
        isMoved = false
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
            buttonDelete.setOnClickListener(null)
            setOnClickListener(null)
            imageViewMotion.setOnTouchListener(null)
            seekBarWeather.setListener(null)

            val item = list[position]

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

            if (isEditStatus) {
                viewDelimiter.visibility = View.VISIBLE
                buttonDelete.visibility = View.VISIBLE
                imageViewMotion.visibility = View.VISIBLE
                buttonDelete.setOnClickListener { delete(item.id, item.name) }
                imageViewMotion.setOnTouchListener { _, event ->
                    if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
                        motion(holder)
                    }
                    return@setOnTouchListener false
                }
            } else {
                setOnClickListener { showDetail(item.id) }
                viewDelimiter.visibility = View.GONE
                buttonDelete.visibility = View.GONE
                imageViewMotion.visibility = View.INVISIBLE
            }

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

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }
        val from = list[fromPosition]
        val to = list[toPosition]

        listMap[from.id] = fromPosition
        listMap[to.id] = toPosition
        isMoved = true
        notifyChange(WeatherModel(list, listMap))
        notifyItemMoved(fromPosition, toPosition)
    }

    fun deleteItem(id: Long): List<WeatherHolder>? {
        val position = listMap[id]
        if (position != null) {
            list.removeAt(position)
            listMap.clear()
            listMap.putAll(list.map { it.id to list.indexOf(it) }.toMap())
            notifyItemRemoved(position)
            return list
        }
        return null
    }
}