package ru.kcoder.weatherhelper.data.network.common

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig

class RetrofitBuilder(
    private val okHttpClient: OkHttpClient,
    private val converterFactory: Converter.Factory) {

    fun build(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL)
            .build()
    }
}