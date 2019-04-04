package ru.kcoder.weatherhelper.toolkit.debug

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for debug http request
 * Print to logs url, headers, body
 */
class DebugInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        log(request.url().toString(), "DEBUG_INTERCEPTOR")
        request.headers().names().forEach {
            log(it, "DEBUG_INTERCEPTOR")
        }
        log(request.body().toString(), "DEBUG_INTERCEPTOR")
        return chain.proceed(request)
    }
}