package ru.kcoder.weatherhelper.data.network.common

import retrofit2.Call
import ru.kcoder.weatherhelper.toolkit.android.LocalException
import ru.kcoder.weatherhelper.toolkit.android.LocalExceptionMsg

fun <T : ApiResponse> Call<T>.executeCall(): T {
    this.execute().body()?.let {
        if (it.cod != null && it.cod.equals("200")) {
            return it
        }
    }
    throw LocalException(LocalExceptionMsg.CANT_CONNECT)
}