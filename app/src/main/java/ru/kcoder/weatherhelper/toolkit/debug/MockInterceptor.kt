package ru.kcoder.weatherhelper.toolkit.debug

import android.content.Context
import okhttp3.*
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import java.io.IOException

/**
 * for json intercept, for debug mode, in case of server connection problems
 * 1. Put json file in debug assets folder
 * 2. name of file should start by the name of REST request
 * (get/post/delete etc...) and url after base url, change "/" to "_" {@see BuildConfig.API_URL}
 * WeatherHandler: get request for url "http://api.openweathermap.org/data/2.5/forecast?q=London",
 * name of file should be mock_server_get_forecast?q=London
 * If file with method name not exist in assets folder, redirecting request to server
 */

class MockInterceptor (val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val path = chain.request().url().uri().path
            .replace(BuildConfig.API_URL, "")
            .replace("/", "_")
        val method = chain.request().method().toLowerCase()
        val json = StringBuilder()
        val fileName = "mock_server_$method$path.json"

        try {
            context.assets.open(fileName).bufferedReader().use { json.append(it.readText()) }
        } catch (e: IOException) {
            return chain.proceed(chain.request())
        }

        return Response.Builder()
            .code(200)
            .message(json.toString())
            .request(chain.request())
            .protocol(Protocol.HTTP_2)
            .body(ResponseBody.create(MediaType.parse("json"), json.toString().toByteArray()))
            .addHeader("content-type", "json")
            .build()
    }
}