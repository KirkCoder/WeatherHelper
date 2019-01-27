package ru.kcoder.weatherhelper.presentation.common

import kotlinx.coroutines.CoroutineScope
import ru.kcoder.weatherhelper.toolkit.android.LocalException

interface BaseInteractor {
    fun initScope(scope: CoroutineScope)
    fun initErrorHandler(handler: (LocalException) -> Unit)
}