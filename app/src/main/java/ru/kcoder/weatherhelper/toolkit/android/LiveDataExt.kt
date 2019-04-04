package ru.kcoder.weatherhelper.toolkit.android

import androidx.lifecycle.Observer

inline fun <T> mObserver(crossinline setValue: (T) -> Unit) = Observer<T> { t -> t?.let(setValue) }