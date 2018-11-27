package ru.kcoder.weatherhelper.data.network.common

import android.content.Context
import okhttp3.OkHttpClient
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.debug.DebugInterceptor
import java.util.concurrent.TimeUnit

class OkHttpBuilder(private val context: Context, private val builder: OkHttpClient.Builder) {

    init {
        builder
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) builder.addInterceptor(DebugInterceptor())
    }

    fun build() = builder.build()
}