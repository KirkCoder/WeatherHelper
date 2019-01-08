package ru.kcoder.weatherhelper.data.network.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kcoder.weatherhelper.data.entity.weather.WeatherHolderFuture

interface WeatherNetworkSource {

    @GET("forecast")
    fun getWeatherByCoordinate(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("APPID")uid: String): Call<WeatherHolderFuture>
}