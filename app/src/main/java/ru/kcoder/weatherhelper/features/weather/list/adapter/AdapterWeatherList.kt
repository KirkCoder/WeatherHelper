package ru.kcoder.weatherhelper.features.weather.list.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolder
import java.util.*

class AdapterWeatherList(
    showDetail: (Long) -> Unit,
    update: (Long) -> Unit,
    delete: (Long, String) -> Unit,
    private val change: (List<WeatherHolder>) -> Unit,
    private val notifyChange: (List<WeatherHolder>) -> Unit,
    motion: (RecyclerView.ViewHolder) -> Unit
) : ListDelegationAdapter<List<WeatherHolder>>(), AdapterSupervisor {

    private var isEditStatus = false
    private var isMoved = false

    init {
        items = emptyList()
        delegatesManager
            .addDelegate(WeatherMainDelegate(this, showDetail, update))
            .addDelegate(WeatherEditDelegate(this, delete, motion))
    }

    fun setData(list: List<WeatherHolder>) {
        if (!isEditStatus) {
            val oldData = items
            items = list

            findDiff(oldData)
        }
    }

    fun setEditStatus(isEdit: Boolean) {
        isEditStatus = isEdit
        notifyDataSetChanged()
        if (!isEdit && isMoved) change(items)
        isMoved = false
    }

    override fun isEditStatus(): Boolean {
        return isEditStatus
    }

    fun deleteItem(id: Long): List<WeatherHolder>? {
        return items.find { it.id == id }?.let {
            val tmp = mutableListOf<WeatherHolder>()
            tmp.addAll(items)
            tmp.removeAt(items.indexOf(it))
            val oldData = items
            items = tmp
            findDiff(oldData)
            items
        }
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        isMoved = true
        notifyChange(items)
        notifyItemMoved(fromPosition, toPosition)
    }

    private fun findDiff(oldData: List<WeatherHolder>) {
        DiffUtil
            .calculateDiff(DiffCallback(items, oldData))
            .dispatchUpdatesTo(this)
    }

    private inner class DiffCallback(
        private val newItems: List<WeatherHolder>,
        private val oldItems: List<WeatherHolder>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return (oldItem.lustUpdate == newItem.lustUpdate
                    && oldItem.isUpdating == newItem.isUpdating)
        }
    }

}