package ru.kcoder.weatherhelper.features.weather.detail.item

import androidx.lifecycle.MutableLiveData
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.features.weather.detail.item.recycler.ClickedItem

class ViewModelWeatherDetailItem(
    private val interactor: ContractWeatherDetailItem.Interactor,
    private val id: Long,
    forceUpdate: Boolean,
    errorSupervisor: ErrorSupervisor
) : ContractWeatherDetailItem.ViewModel(interactor, errorSupervisor) {

    override val weather = MutableLiveData<List<Any>>()
    override val status = MutableLiveData<Boolean>()
    override val checked = MutableLiveData<Int>()

    init {
        updateWeather(id, forceUpdate)
    }

    private fun updateWeather(whId: Long, update: Boolean) {
        interactor.updateWeather(whId, update, { list ->
            getChecked(list)?.let{ checked.value = it }
            weather.value = list
        }, {
            if (update) {
                status.value = it
            }
        })
    }

    private fun getChecked(list: List<Any>): Int? {
        for ((i) in list.withIndex()) {
            val item = list[i]
            if (item is ClickedItem && item.isClicked()) return i
        }
        return null
    }

    override fun clickInform(position: Int?) {
        checked.value = position
    }

    override fun forceUpdate() {
        updateWeather(id, true)
    }
}