package ru.kcoder.weatherhelper.toolkit.android

import androidx.lifecycle.Observer

inline fun <T> set(crossinline setValue: (T) -> Unit) = Observer<T> { t -> t?.let(setValue) }