package ru.kcoder.weatherhelper.toolkit.debug

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for debug http request
 *
 */
class DebugInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d("DEBUG_INTERCEPTOR", request.url().toString())
        return chain.proceed(request)
    }
}