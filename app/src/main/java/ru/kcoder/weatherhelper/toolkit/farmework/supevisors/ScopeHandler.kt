package ru.kcoder.weatherhelper.toolkit.farmework.supevisors

import kotlinx.coroutines.CoroutineScope

interface ScopeHandler {
    val scope: CoroutineScope
    val globalScope: CoroutineScope
}