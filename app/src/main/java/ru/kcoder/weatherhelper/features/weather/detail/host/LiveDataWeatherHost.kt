package ru.kcoder.weatherhelper.features.weather.detail.host

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherDetailModel
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataWeatherHost : MediatorLiveData<WeatherDetailModel>() {

    private val isObserve = AtomicBoolean(true)

    override fun observe(owner: LifecycleOwner, observer: Observer<in WeatherDetailModel>) {

        super.observe(owner, Observer<WeatherDetailModel> {
            if (isObserve.compareAndSet(true, false)) {
                observer.onChanged(it)
                value = it.apply { selectedItem = null }
            } else {
                observer.onChanged(it.apply { selectedItem = null })
            }
        })
    }
}