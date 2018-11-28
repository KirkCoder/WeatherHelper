package ru.kcoder.weatherhelper.data.network.common

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig

class RetrofitBuilder(
    private val okHttpClient: OkHttpClient) {

    fun build(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.API_URL)
            .build()
    }
}