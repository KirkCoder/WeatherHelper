package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "weather_holder"
)
class WeatherHolder {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var position: Int = 0
    var lat: Double = 0.0
    var lon: Double = 0.0
    var name: String = ""


    var cod: String? = null
    var message: Double? = null
    var cnt: Double? = null

    @Ignore
    @SerializedName("list")
    var data: List<Data>? = null
    @Ignore
    var city: City? = null

}