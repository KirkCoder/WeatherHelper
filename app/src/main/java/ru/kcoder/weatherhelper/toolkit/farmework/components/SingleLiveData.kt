package ru.kcoder.weatherhelper.toolkit.farmework.components

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean


class SingleLiveData<T> : MutableLiveData<T>() {

    private val isObserve = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        super.observe(owner, Observer<T> {
            if (isObserve.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    override fun setValue(t: T?) {
        isObserve.set(true)
        super.setValue(t)
    }
}