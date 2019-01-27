package ru.kcoder.weatherhelper.domain.common

import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.presentation.common.BaseInteractor
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.debug.log
import java.io.IOException

abstract class AbstractInteractor(
    protected val settingsRepository: SettingsRepository
) : BaseInteractor {

    private var coroutineScope: CoroutineScope = GlobalScope
    private var errorHandler: ((LocalException) -> Unit)? = null

    override fun initScope(scope: CoroutineScope) {
        coroutineScope = scope
    }

    override fun initErrorHandler(handler: (LocalException) -> Unit) {
        errorHandler = handler
    }

    protected fun onError(error: Throwable){
        if(error is LocalException) errorHandler?.invoke(error)
    }

    fun <B> loading(
        load: () -> B,
        callback: (data: B) -> Unit,
        errorCallback: ((Throwable) -> Unit)? = null,
        scope: CoroutineScope = coroutineScope
    ) {
        scope.launch {
            try {
                callback(
                    withContext(Dispatchers.IO) { load() }
                )
            } catch (err: Throwable) {
                errorParser(err, errorCallback)
            }
        }
    }

    fun <B> loadingProgress(
        load: () -> B,
        callback: (data: B) -> Unit,
        loadingStatus: (Boolean) -> Unit,
        errorCallback: ((Throwable) -> Unit)? = null,
        scope: CoroutineScope = coroutineScope
    ) {
        loadingStatus(true)
        loading(load, {
            loadingStatus(false)
            callback(it)
        }, { err ->
            loadingStatus(false)
            errorParser(err, errorCallback)
        }, scope)
    }

    fun runWithSettings(
        callback: (Settings) -> Unit,
        scope: CoroutineScope = coroutineScope
    ) {
        scope.launch {
            try {
                callback(
                    withContext(Dispatchers.IO) {
                        settingsRepository.getSettings()
                    }
                )
            } catch (err: Throwable) {
                errorParser(err, null)
            }
        }
    }

    fun <B> uploading(
        upload: () -> B,
        scope: CoroutineScope = coroutineScope
    ) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) { upload() }
            } catch (err: Throwable) {
                errorParser(err, null)
            }
        }
    }

    private fun errorParser(err: Throwable, errorCallback: ((Throwable) -> Unit)?) {
        if (BuildConfig.DEBUG) {
            log(err.message ?: err.toString())
            err.printStackTrace()
        }
        when (err) {
            is LocalException -> errorCallback?.invoke(err) ?: errorHandler?.invoke(err)
            is IOException -> LocalException(LocalExceptionMsg.CANT_CONNECT)
                .also { errorCallback?.invoke(it) ?: errorHandler?.invoke(it) }
            else -> errorCallback?.invoke(err) ?: log("Unexpected exception ${err.message ?: err.toString()}")
        }
    }
}