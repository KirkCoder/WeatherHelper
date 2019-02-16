package ru.kcoder.weatherhelper.features.weather.detail.host

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.kcoder.weatherhelper.data.entity.weather.detail.SelectedItem
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherDetailModel
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherPosition
import ru.kcoder.weatherhelper.features.weather.detail.item.FragmentWeatherDetailItem

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var list = emptyList<WeatherPosition>()

    private var selected: SelectedItem? = null

    fun setData(item: WeatherDetailModel){
        list = item.list
        selected = item.selectedItem
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        val tmpSelected = selected
        return if (tmpSelected != null && tmpSelected.needUpdate && tmpSelected.position == position) {
            selected = null
            FragmentWeatherDetailItem.newInstance(list[position].id, true)
        } else {
            FragmentWeatherDetailItem.newInstance(list[position].id, false)
        }
    }

    override fun getCount() = list.size
}