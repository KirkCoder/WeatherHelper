package ru.kcoder.weatherhelper.data.network.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kcoder.weatherhelper.data.entity.weather.network.Data
import ru.kcoder.weatherhelper.data.entity.weather.network.WeatherForecast

interface WeatherNetworkSource {

    @GET("forecast")
    fun getWeatherForecast(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("APPID")uid: String): Call<WeatherForecast>

    @GET("weather")
    fun getWeather(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("APPID")uid: String): Call<Data>
}