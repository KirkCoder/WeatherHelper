package ru.kcoder.weatherhelper.data.entity.weather

import com.google.gson.annotations.SerializedName

class Data {

    var dt: Long? = null
    var main: Main? = null
    var weather: List<Weather>? = null
    var clouds: Clouds? = null
    var wind: Wind? = null
    var sys: Sys? = null
    @SerializedName("dt_txt")
    var dtTxt: String? = null
    var rain: Rain? = null

}