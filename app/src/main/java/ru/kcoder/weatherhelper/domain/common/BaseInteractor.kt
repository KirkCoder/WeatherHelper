package ru.kcoder.weatherhelper.domain.common

import kotlinx.coroutines.*
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg
import ru.kcoder.weatherhelper.toolkit.debug.log
import java.io.IOException

abstract class BaseInteractor {

    fun <R, B> loading(
        repository: R,
        scope: CoroutineScope,
        load: R.() -> B,
        callback: (data: B?, error: LocalException?) -> Unit
    ) {
        scope.launch {
            try {
                callback(
                    withContext(Dispatchers.IO) {
                        load(repository)
                    }, null
                )
            } catch (err: Throwable) {
                if (BuildConfig.DEBUG) {
                    log(err.message ?: err.toString())
                    err.printStackTrace()
                }

                when (err) {
                    is LocalException -> callback(null, err)
                    is IOException -> callback(
                        null,
                        LocalException(LocalExceptionMsg.CANT_CONNECT)
                    )
                    else -> log("Unexpected exception ${err.message ?: err.toString()}")
                }
            }
        }
    }

    fun <R, B> loadingProgress(
        repository: R,
        scope: CoroutineScope,
        load: R.() -> B, callback: (data: B?, error: LocalException?, isLoading: Boolean) -> Unit
    ) {
        callback(null, null, true)
        loading(repository, scope, load) { data, error ->
            data?.let { callback(it, null, false) }
            error?.let { callback(null, it, false) }
        }
    }

    fun <R, B> uploading(
        repository: R,
        scope: CoroutineScope,
        upload: R.() -> B
    ) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) { upload(repository) }
            } catch (err: Throwable) {
                if (BuildConfig.DEBUG) {
                    log(err.message ?: err.toString())
                    err.printStackTrace()
                }
            }
        }
    }
}