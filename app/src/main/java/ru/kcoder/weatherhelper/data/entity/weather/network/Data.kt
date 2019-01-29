package ru.kcoder.weatherhelper.data.entity.weather.network

import com.google.gson.annotations.SerializedName
import ru.kcoder.weatherhelper.data.network.common.ApiResponse

class Data: ApiResponse {

    var dt: Long? = null
    @SerializedName("dt_txt")
    var dtTxt: String? = null

    var weather: List<Weather>? = null
    var clouds: Clouds? = null
    var sys: Sys? = null
    var wind: Wind? = null
    var main: Main? = null
    var rain: Rain? = null


    override var cod: Int? = null
}