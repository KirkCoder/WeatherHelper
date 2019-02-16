package ru.kcoder.weatherhelper.toolkit.farmework.supevisors

import kotlinx.coroutines.*

class ScopeHandlerImpl : ScopeHandler {
    override val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    override val globalScope = GlobalScope
    override fun cancel() {
        scope.coroutineContext.cancel()
    }
}