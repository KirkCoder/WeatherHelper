package ru.kcoder.weatherhelper.toolkit.farmework.supevisors

import kotlinx.coroutines.CoroutineExceptionHandler
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.farmework.components.SingleLiveData
import java.io.IOException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class ErrorSupervisorImpl : ErrorSupervisor {
    override val errorLiveData = SingleLiveData<Int>()

    override fun onError(error: LocalException) {
        errorLiveData.value = error.msg.resourceString
    }

    override fun getCoroutineErrorHandler(
        customHandler: ((Throwable) -> Unit)?
    ): CoroutineExceptionHandler =
        object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
            override fun handleException(context: CoroutineContext, exception: Throwable) {
                if (BuildConfig.DEBUG) {
                    log(exception.message ?: exception.toString())
                    exception.printStackTrace()
                }
                when (exception) {
                    is LocalException -> customHandler?.invoke(exception) ?: onError(exception)
                    is IOException -> LocalException(LocalExceptionMsg.CANT_CONNECT)
                        .also { customHandler?.invoke(it) ?: onError(it) }
                    else -> customHandler?.invoke(exception) ?: log(
                        "Unexpected exception ${exception.message ?: exception.toString()}"
                    )
                }
            }
        }
}