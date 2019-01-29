package ru.kcoder.weatherhelper.data.entity.weather.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rain {
    @SerializedName("3h")
    @Expose
    var threeH:Double? = null

    @SerializedName("1h")
    @Expose
    var oneH:Double? = null

}