package ru.kcoder.weatherhelper.toolkit.android

import androidx.annotation.IdRes
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import java.lang.Exception

class LocalException(val msg: LocalExceptionMsg): Exception()

enum class LocalExceptionMsg(@IdRes val resourceString: Int) {
    CANT_LOAD_ADDRESS(R.string.place_common_cant_load_address),
    CANT_CONNECT(R.string.place_common_error_cant_connect),
    PLACE_EXIST(R.string.place_add_error),
    UNEXPECTED_ERROR(R.string.error_unexpected)
}