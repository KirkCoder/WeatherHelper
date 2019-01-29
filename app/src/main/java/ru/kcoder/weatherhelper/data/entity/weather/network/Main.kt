package ru.kcoder.weatherhelper.data.entity.weather.network

import com.google.gson.annotations.SerializedName

class Main {
    var temp: Double? = null
    @SerializedName("temp_min")
    var tempMin: Double? = null
    @SerializedName("temp_max")
    var tempMax: Double? = null
    var pressure: Double? = null
    @SerializedName("sea_level")
    var seaLevel: Double? = null
    @SerializedName("grnd_level")
    var grndLevel: Double? = null
    var humidity: Double? = null
    @SerializedName("temp_kf")
    var tempKf: Double? = null
}