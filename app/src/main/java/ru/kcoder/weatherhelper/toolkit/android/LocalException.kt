package ru.kcoder.weatherhelper.toolkit.android

import android.support.annotation.IdRes
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import java.lang.Exception

class LocalException(val msg: LocalExceptionMsg): Exception()

enum class LocalExceptionMsg(@IdRes val resourceString: Int) {
    CANT_LOAD_ADDRESS(R.string.cant_load_address)
}