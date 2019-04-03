package ru.kcoder.weatherhelper.toolkit.farmework.supevisors

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import ru.kcoder.weatherhelper.toolkit.android.LocalException

interface ErrorSupervisor {
    val errorLiveData: LiveData<Int>
    fun onError(error: LocalException)

    fun getCoroutineErrorHandler(
        customHandler: ((Throwable) -> Unit)? = null
    ): CoroutineExceptionHandler
}