package ru.kcoder.weatherhelper.toolkit.farmework.supevisors

import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.farmework.components.SingleLiveData

class ErrorSupervisorImpl: ErrorSupervisor {
    override val errorLiveData = SingleLiveData<Int>()

    override fun onError(error: LocalException) {
        errorLiveData.value = error.msg.resourceString
    }
}