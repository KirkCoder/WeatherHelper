package ru.kcoder.weatherhelper.data.entity.weather

import com.google.gson.annotations.SerializedName
import ru.kcoder.weatherhelper.data.network.common.ApiResponseForecast

class WeatherForecast: ApiResponseForecast {

    override var cod: String? = null
    var message: Double? = null
    var cnt: Double? = null

    @SerializedName("list")
    var data: List<Data>? = null
    var city: City? = null

}