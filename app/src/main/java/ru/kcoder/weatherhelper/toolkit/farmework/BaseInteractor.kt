package ru.kcoder.weatherhelper.toolkit.farmework

import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeController
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

abstract class BaseInteractor(
    private val settingsRepository: SettingsRepository,
    protected val scopeHandler: ScopeHandler,
    private val errorSupervisor: ErrorSupervisor
) : ScopeController {

    protected fun onError(error: Throwable) {
        if (error is LocalException) errorSupervisor.onError(error)
    }

    fun <B> loading(
        load: suspend (CoroutineScope) -> B,
        callback: ((B) -> Unit)? = null,
        errorCallback: ((Throwable) -> Unit)? = null
    ) {
        scopeHandler.scope.launch(
            errorSupervisor.getCoroutineErrorHandler(errorCallback)
        ) {
            val res = withContext(Dispatchers.IO) { load(this) }
            callback?.invoke(res)
        }
    }

    fun <B> loadingProgress(
        load: suspend (CoroutineScope) -> B,
        callback: ((B) -> Unit)? = null,
        loadingStatus: (Boolean) -> Unit,
        errorCallback: ((Throwable) -> Unit)? = null
    ) {
        loadingStatus(true)
        loading(load, {
            loadingStatus(false)
            callback?.invoke(it)
        }, { err ->
            loadingStatus(false)
            errorCallback?.invoke(err)
        })
    }

    fun runWithSettings(
        callback: (Settings) -> Unit
    ) {
        scopeHandler.scope.launch(errorSupervisor.getCoroutineErrorHandler()) {
            callback(
                withContext(Dispatchers.IO) { settingsRepository.getSettings() }
            )
        }
    }

    fun <B> uploading(
        upload: () -> B,
        success: ((Boolean) -> Unit)? = null,
        scope: CoroutineScope = scopeHandler.globalScope
    ) {
        scope.launch(errorSupervisor.getCoroutineErrorHandler()) {
            withContext(Dispatchers.IO) { upload() }
            success?.invoke(true)
        }
    }

    override fun cancel() {
        scopeHandler.cancel()
    }
}