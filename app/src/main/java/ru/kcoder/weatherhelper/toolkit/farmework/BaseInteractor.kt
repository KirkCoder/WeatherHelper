package ru.kcoder.weatherhelper.toolkit.farmework

import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import java.io.IOException

abstract class BaseInteractor(
    private val settingsRepository: SettingsRepository,
    protected val scopeHandler: ScopeHandler,
    private val errorSupervisor: ErrorSupervisor
) : ScopeController {

    protected fun onError(error: Throwable) {
        if (error is LocalException) errorSupervisor.onError(error)
    }

    fun <B> loading(
        load: suspend () -> B,
        callback: ((data: B) -> Unit)? = null,
        errorCallback: ((Throwable) -> Unit)? = null
    ) {
        scopeHandler.scope.launch {
            try {
                val res = withContext(Dispatchers.IO) { load() }
                callback?.invoke(res)
            } catch (err: Throwable) {
                errorParser(err, errorCallback)
            }
        }
    }

    fun <B> loadingProgress(
        load: suspend () -> B,
        callback: ((data: B) -> Unit)? = null,
        loadingStatus: (Boolean) -> Unit,
        errorCallback: ((Throwable) -> Unit)? = null
    ) {
        loadingStatus(true)
        loading(load, {
            loadingStatus(false)
            callback?.invoke(it)
        }, { err ->
            loadingStatus(false)
            errorParser(err, errorCallback)
        })
    }

    fun runWithSettings(
        callback: (Settings) -> Unit
    ) {
        scopeHandler.scope.launch {
            try {
                callback(
                    withContext(Dispatchers.IO) {
                        settingsRepository.getSettings()
                    }
                )
            } catch (err: Throwable) {
                err.printStackTrace()
                errorParser(err, null)
            }
        }
    }

    fun <B> uploading(
        upload: () -> B,
        success: ((Boolean) -> Unit)? = null,
        scope: CoroutineScope = scopeHandler.globalScope
    ) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    upload()
                    Unit
                }
                success?.invoke(true)
            } catch (err: Throwable) {
                errorParser(err, null)
                success?.invoke(false)
            }
        }
    }

    private fun errorParser(err: Throwable, errorCallback: ((Throwable) -> Unit)?) {
        if (BuildConfig.DEBUG) {
            log(err.message ?: err.toString())
            err.printStackTrace()
        }
        when (err) {
            is LocalException -> errorCallback?.invoke(err) ?: errorSupervisor.onError(err)
            is IOException -> LocalException(LocalExceptionMsg.CANT_CONNECT)
                .also { errorCallback?.invoke(it) ?: errorSupervisor.onError(it) }
            else -> errorCallback?.invoke(err) ?: log("Unexpected exception ${err.message ?: err.toString()}")
        }
    }

    override fun cancel() {
        scopeHandler.cancel()
    }
}