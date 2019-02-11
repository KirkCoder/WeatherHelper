package ru.kcoder.weatherhelper.toolkit.farmework.supevisors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob

class ScopeHandlerImpl : ScopeHandler {
    override val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    override val globalScope = GlobalScope
}