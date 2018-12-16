package ru.kcoder.weatherhelper.domain.place

import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.domain.common.BaseInteractor
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.debug.log

class PlaceAddInteractorImpl(
    private val repository: PlaceRepository
) : BaseInteractor(), PlaceAddInteractor {

    override fun getAddress(
        lat: Double, lon: Double,
        callback: (Pair<String?, String?>) -> Unit,
        errorCallback: (Int) -> Unit
    ) {
        loading(repository, {
            getAddress(lat, lon)
        }, { data, error ->
            data?.let { callback(it) }
            error?.let {
                if (BuildConfig.DEBUG) it.printStackTrace()
                if (it is LocalException) errorCallback(it.msg.resourceString)
            }
        })
    }
}