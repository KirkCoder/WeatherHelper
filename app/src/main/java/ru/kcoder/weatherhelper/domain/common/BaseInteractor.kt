package ru.kcoder.weatherhelper.domain.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.debug.log

abstract class BaseInteractor {

    fun <R, B> loading(
        repository: R,
        load: R.() -> B,
        callback: (data: B?, error: LocalException?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val job = GlobalScope.async(Dispatchers.IO) { load.invoke(repository) }
                callback(job.await(), null)
            } catch (err: Throwable) {
                if (BuildConfig.DEBUG) {
                    log(err.message ?: err.toString())
                    err.printStackTrace()
                }
                if (err is LocalException) callback(null, err)
            }
        }
    }

    fun <R, B> loadingProgress(
        repository: R,
        load: R.() -> B, callback: (data: B?, error: LocalException?, status: Int) -> Unit
    ) {
        callback(null, null, LOADING)
        loading(repository, load) { data, error ->
            data?.let { callback(it, null, DONE) }
            error?.let { callback(null, it, FAIL) }
        }
    }

    companion object {
        const val LOADING = 1
        const val DONE = 2
        const val FAIL = 2
    }
}