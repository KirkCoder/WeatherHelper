package ru.kcoder.weatherhelper.toolkit.farmework.components

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Async(
    val scope: CoroutineScope,
    val exceptionHandler: CoroutineExceptionHandler
) {
    inline fun invoke(crossinline work: () -> Unit) {
        scope.launch(Dispatchers.Default + exceptionHandler) {
            work.invoke()
        }
    }
}